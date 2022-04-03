package com.mjanvier.ariadnexttp.service;

import com.mjanvier.ariadnexttp.idcheck.ResultResponseDTO;
import com.mjanvier.ariadnexttp.idcheck.api.AnalysisApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IdCheckServiceTest {

    @Mock
    private AnalysisApi analysisApi;
    @Mock
    private UserInfoService userInfoService;
    @Mock
    private UserValidationService userValidationService;

    @InjectMocks
    private IdCheckService idCheckService;

    @Test
    void analyseImage_shouldReturnTrue() {
        final String userName = "theUserName";
        when(userInfoService.getUserName()).thenReturn(userName);

        final ResultResponseDTO resultResponseDTO = new ResultResponseDTO();
        when(analysisApi.postImage(any(), eq(false), eq(null))).thenReturn(resultResponseDTO);

        when(userValidationService.isAllIdChecksAreOk(resultResponseDTO)).thenReturn(true);
        when(userValidationService.isDocNamesMatchSocialInfos(userName, resultResponseDTO)).thenReturn(true);

        assertTrue(idCheckService.analyseImage("myimageBase64"));

        verify(userInfoService).getUserName();
        verify(analysisApi).postImage(any(), eq(false), eq(null));
        verify(userValidationService).isAllIdChecksAreOk(resultResponseDTO);
        verify(userValidationService).isDocNamesMatchSocialInfos(userName, resultResponseDTO);
    }

    @Test
    void analyseImage_shouldReturnFalseBecauseOfNameMismatch() {
        final String userName = "theUserName";
        when(userInfoService.getUserName()).thenReturn(userName);

        final ResultResponseDTO resultResponseDTO = new ResultResponseDTO();
        when(analysisApi.postImage(any(), eq(false), eq(null))).thenReturn(resultResponseDTO);

        when(userValidationService.isAllIdChecksAreOk(resultResponseDTO)).thenReturn(true);
        when(userValidationService.isDocNamesMatchSocialInfos(userName, resultResponseDTO)).thenReturn(false);

        assertFalse(idCheckService.analyseImage("myimageBase64"));

        verify(userInfoService).getUserName();
        verify(analysisApi).postImage(any(), eq(false), eq(null));
        verify(userValidationService).isAllIdChecksAreOk(resultResponseDTO);
        verify(userValidationService).isDocNamesMatchSocialInfos(userName, resultResponseDTO);
    }

    @Test
    void analyseImage_shouldReturnFalseBecauseOfIdCHeckProblem() {
        final String userName = "theUserName";
        when(userInfoService.getUserName()).thenReturn(userName);

        final ResultResponseDTO resultResponseDTO = new ResultResponseDTO();
        when(analysisApi.postImage(any(), eq(false), eq(null))).thenReturn(resultResponseDTO);

        when(userValidationService.isAllIdChecksAreOk(resultResponseDTO)).thenReturn(false);

        assertFalse(idCheckService.analyseImage("myimageBase64"));

        verify(userInfoService).getUserName();
        verify(analysisApi).postImage(any(), eq(false), eq(null));
        verify(userValidationService).isAllIdChecksAreOk(resultResponseDTO);
        verifyNoMoreInteractions(userValidationService);
    }

}