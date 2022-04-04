package toy.lms.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.lms.common.mapper.CommonMapper;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final CommonMapper commonMapper;
}
