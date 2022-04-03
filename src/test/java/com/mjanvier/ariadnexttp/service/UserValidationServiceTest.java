package com.mjanvier.ariadnexttp.service;

import com.mjanvier.ariadnexttp.idcheck.CheckReportSummaryDTO;
import com.mjanvier.ariadnexttp.idcheck.ControlDTO;
import com.mjanvier.ariadnexttp.idcheck.HolderDetailDTO;
import com.mjanvier.ariadnexttp.idcheck.ResultResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserValidationServiceTest {

    @InjectMocks
    private UserValidationService userValidationService;

    @Test
    void isDocNamesMatchSocialInfos_shouldReturnTrue() {

        final HolderDetailDTO holderDetail = new HolderDetailDTO();
        holderDetail.setFirstName(Collections.singletonList("Mathieu"));
        holderDetail.setLastName(Collections.singletonList("Janvier"));

        final ResultResponseDTO resultResponseDTO = new ResultResponseDTO();
        resultResponseDTO.setHolderDetail(holderDetail);
        assertTrue(userValidationService.isDocNamesMatchSocialInfos("mathieu janvier", resultResponseDTO));

    }

    @Test
    void isDocNamesMatchSocialInfos_shouldReturnFalse() {

        final HolderDetailDTO holderDetail = new HolderDetailDTO();
        holderDetail.setFirstName(Collections.singletonList("Mathieu"));
        holderDetail.setLastName(Collections.singletonList("Fevrier"));

        final ResultResponseDTO resultResponseDTO = new ResultResponseDTO();
        resultResponseDTO.setHolderDetail(holderDetail);
        assertFalse(userValidationService.isDocNamesMatchSocialInfos("mathieu janvier", resultResponseDTO));

    }

    @Test
    void isAllIdChecksAreOk_shouldReturnTrue() {
        final CheckReportSummaryDTO checkReportSummary = new CheckReportSummaryDTO();
        final ControlDTO checkItem1 = new ControlDTO();
        checkItem1.setResult(ControlDTO.ResultEnum.OK);
        checkReportSummary.addCheckItem(checkItem1);

        final ControlDTO checkItem2 = new ControlDTO();
        checkItem1.setResult(ControlDTO.ResultEnum.NONE);
        checkReportSummary.addCheckItem(checkItem2);

        final ResultResponseDTO resultResponseDTO = new ResultResponseDTO();
        resultResponseDTO.setCheckReportSummary(checkReportSummary);

        assertTrue(userValidationService.isAllIdChecksAreOk(resultResponseDTO));
    }

    @Test
    void isAllIdChecksAreOk_shouldReturnFalseBecauseOfErrorResult() {
        final CheckReportSummaryDTO checkReportSummary = new CheckReportSummaryDTO();
        final ControlDTO checkItem1 = new ControlDTO();
        checkItem1.setResult(ControlDTO.ResultEnum.OK);
        checkReportSummary.addCheckItem(checkItem1);

        final ControlDTO checkItem2 = new ControlDTO();
        checkItem1.setResult(ControlDTO.ResultEnum.ERROR);
        checkReportSummary.addCheckItem(checkItem2);

        final ResultResponseDTO resultResponseDTO = new ResultResponseDTO();
        resultResponseDTO.setCheckReportSummary(checkReportSummary);

        assertFalse(userValidationService.isAllIdChecksAreOk(resultResponseDTO));
    }

    @Test
    void isAllIdChecksAreOk_shouldReturnFalseBecauseOfWarningResult() {
        final CheckReportSummaryDTO checkReportSummary = new CheckReportSummaryDTO();
        final ControlDTO checkItem1 = new ControlDTO();
        checkItem1.setResult(ControlDTO.ResultEnum.OK);
        checkReportSummary.addCheckItem(checkItem1);

        final ControlDTO checkItem2 = new ControlDTO();
        checkItem1.setResult(ControlDTO.ResultEnum.WARNING);
        checkReportSummary.addCheckItem(checkItem2);

        final ResultResponseDTO resultResponseDTO = new ResultResponseDTO();
        resultResponseDTO.setCheckReportSummary(checkReportSummary);

        assertFalse(userValidationService.isAllIdChecksAreOk(resultResponseDTO));
    }
}