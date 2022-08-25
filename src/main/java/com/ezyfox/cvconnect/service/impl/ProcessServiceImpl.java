package com.ezyfox.cvconnect.service.impl;

import com.ezyfox.cvconnect.constant.EntityStatus;
import com.ezyfox.cvconnect.constant.ProcessCode;
import com.ezyfox.cvconnect.converter.DataToEntityConverter;
import com.ezyfox.cvconnect.converter.EntityToResponseConverter;
import com.ezyfox.cvconnect.entity.Process;
import com.ezyfox.cvconnect.exception.ResourceNotFoundException;
import com.ezyfox.cvconnect.model.AddProcessData;
import com.ezyfox.cvconnect.model.EditProcessData;
import com.ezyfox.cvconnect.repository.ProcessRepository;
import com.ezyfox.cvconnect.response.ProcessResponse;
import com.ezyfox.cvconnect.service.ProcessService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

@EzySingleton
@AllArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final ProcessRepository processRepository;
    private final DataToEntityConverter dataToEntityConverter;
    private final EntityToResponseConverter entityToResponseConverter;

    @Override
    public ResponseEntity addProcess(AddProcessData addProcessData) {
        processRepository.save(dataToEntityConverter.dataToProcess(addProcessData));
        return ResponseEntity.noContent();
    }

    @Override
    public ResponseEntity editProcess(EditProcessData editProcessData) {
        Process processById = processRepository.findById(editProcessData.getProcessId());
        if (processById == null) {
            throw new ResourceNotFoundException("Process");
        }
        if (editProcessData.getProcessCode() != null) {
            processById.setCode(editProcessData.getProcessCode());
        }
        if (!StringUtil.isBlank(editProcessData.getMeaning())) {
            processById.setMeaning(editProcessData.getMeaning());
        }
        processRepository.save(processById);
        return ResponseEntity.noContent();
    }

    @Override
    public void updateProcessNext(Process process) {
        switch (process.getCode()) {
            case INIT: {
                process.setCode(ProcessCode.SEND_CV_TO_AGENCY);
                break;
            }
            case SEND_CV_TO_AGENCY: {
                process.setCode(ProcessCode.SEND_CV_TO_COMPANY);
                break;
            }
            case SEND_CV_TO_COMPANY: {
                process.setCode(ProcessCode.SUCCESS);
                break;
            }
            default:
                break;
        }
        processRepository.save(process);
    }

    @Override
    public void updateProcessPrev(Process process) {
        switch (process.getCode()) {
            case SEND_CV_TO_AGENCY: {
                process.setCode(ProcessCode.INIT);
                break;
            }
            case SEND_CV_TO_COMPANY: {
                process.setCode(ProcessCode.SEND_CV_TO_AGENCY);
                break;
            }
            case FAIL:
            case SUCCESS: {
                process.setCode(ProcessCode.SEND_CV_TO_COMPANY);
                break;
            }
            default:
                break;
        }
        processRepository.save(process);
    }

    @Override
    public void updateProcessCode(EditProcessData editProcessData, ProcessCode processCode) {
        Process processById = processRepository.findById(editProcessData.getProcessId());
        if (processById == null) {
            throw new ResourceNotFoundException("Process");
        }
        if (!processById.getStatus().equals(EntityStatus.ACTIVE)) {
            throw new ResourceNotFoundException("Process Active");
        }
        if (editProcessData.getProcessCode() != null) {
            processById.setCode(editProcessData.getProcessCode());
        }
        processRepository.save(processById);
    }

    @Override
    public List<ProcessResponse> getByProcessCode(ProcessCode processCode) {
        return processRepository
            .findByProcessCode(processCode)
            .stream()
            .map(entityToResponseConverter::toProcessResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProcessResponse> getByStatus(EntityStatus status) {
        return processRepository
            .findByStatus(status)
            .stream()
            .map(entityToResponseConverter::toProcessResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProcessResponse> getAll() {
        return processRepository
            .findAll()
            .stream()
            .map(entityToResponseConverter::toProcessResponse)
            .collect(Collectors.toList());
    }

    @Override
    public ProcessResponse getById(long processId) {
        return entityToResponseConverter.toProcessResponse(processRepository.findById(processId));
    }
}