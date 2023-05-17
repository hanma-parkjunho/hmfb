package hmfb.core.service;

import org.springframework.stereotype.Service;

import hmfb.core.dto.FirmReturnDto;

@Service
public interface BaseService {
    public void process(FirmReturnDto dto);
}
