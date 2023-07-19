package hmfb.core.service;

import org.springframework.stereotype.Service;

import hmfb.core.dto.StdFirmReturnDto;

@Service
public interface StdBaseService {
    public void process(StdFirmReturnDto dto);
}
