package toy.lms.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "toy.lms", sqlSessionFactoryRef = "postgreSqlSessionFactory")
@EnableTransactionManagement
public class PostgreSqlConfig {

  @Bean(name = "postgreDataSource")
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.hikari") // application-prod.yml
  public DataSource PostgreDatasource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "postgreSqlSessionFactory")
  @Primary
  public SqlSessionFactory sqlSessionFactory(
      @Qualifier("postgreDataSource") DataSource dataSource,
      ApplicationContext applicationContext
  ) throws Exception {
    final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource);
    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    configuration.setMapUnderscoreToCamelCase(true);
    sessionFactoryBean.setConfiguration(configuration);
    sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/*/*.xml"));
    return sessionFactoryBean.getObject();
  }

  @Bean(name = "postgreSqlSessionTemplate")
  @Primary
  public SqlSessionTemplate sqlSessionTemplate(
      SqlSessionFactory sqlSessionFactory
  ) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Bean(name = "postgreTransactionManager")
  @Primary
  public PlatformTransactionManager transactionManager(
      @Qualifier("postgreDataSource") DataSource dataSource)
  {
    return new DataSourceTransactionManager(dataSource);
  }
}
