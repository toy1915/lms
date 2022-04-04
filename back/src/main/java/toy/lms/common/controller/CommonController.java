package toy.lms.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.lms.common.service.CommonService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/common")
public class CommonController {
    private final CommonService commonService;
}
