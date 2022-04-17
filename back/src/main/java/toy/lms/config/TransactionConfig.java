package toy.lms.config;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Aspect
@Configuration
@RequiredArgsConstructor
public class TransactionConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionConfig.class);
    private static final int TX_METHOD_TIMEOUT = 60;

    private final DataSourceTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice(){
        TransactionInterceptor txAdvice = new TransactionInterceptor();
        Properties txAttributes = new Properties();

        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));

        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        readOnlyAttribute.setReadOnly(true);
        readOnlyAttribute.setTimeout(TX_METHOD_TIMEOUT);

        RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setTimeout(TX_METHOD_TIMEOUT);

        String readOnlyTransactionAttributesDefinition = readOnlyAttribute.toString();
        String writeTransactionAttributesDefinition = writeAttribute.toString();

        LOGGER.info("Read Only Attributes :: {}", readOnlyTransactionAttributesDefinition);
        LOGGER.info("Write Attributes :: {}", writeTransactionAttributesDefinition);

        //read-only
        txAttributes.setProperty("get*", readOnlyTransactionAttributesDefinition);
        txAttributes.setProperty("check*", readOnlyTransactionAttributesDefinition);
        txAttributes.setProperty("reset*", readOnlyTransactionAttributesDefinition);

        // write rollback-rule
        txAttributes.setProperty("register*", writeTransactionAttributesDefinition);
        txAttributes.setProperty("modify*", writeTransactionAttributesDefinition);
        txAttributes.setProperty("remove*", writeTransactionAttributesDefinition);
        txAttributes.setProperty("set*", writeTransactionAttributesDefinition);
        txAttributes.setProperty("process*", writeTransactionAttributesDefinition);


        txAdvice.setTransactionAttributes(txAttributes);
        txAdvice.setTransactionManager(transactionManager);

        return txAdvice;
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* toy.lms..service..*Service.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
