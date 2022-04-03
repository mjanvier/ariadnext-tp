package com.mjanvier.ariadnexttp.service;


import com.mjanvier.ariadnexttp.idcheck.ImageRequestDTO;
import com.mjanvier.ariadnexttp.idcheck.ResultResponseDTO;
import com.mjanvier.ariadnexttp.idcheck.api.AnalysisApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RequiredArgsConstructor
@Service
public class IdCheckService {

    private final AnalysisApi analysisApi;
    private final UserInfoService userInfoService;
    private final UserValidationService userValidationService;

    public boolean analyseImage(String imageBase64String) {
        final String userName = userInfoService.getUserName();

        log.info("create task for {}", userName);
        final ImageRequestDTO imageRequestDTO = new ImageRequestDTO();
        imageRequestDTO.frontImage(imageBase64String);
        imageRequestDTO.rectoImageCropped(false);
        imageRequestDTO.signatureImageCropped(false);
        imageRequestDTO.faceImageCropped(false);

        try {
            final ResultResponseDTO resultResponseDTO = analysisApi.postImage(imageRequestDTO, false, null);

            return userValidationService.isAllIdChecksAreOk(resultResponseDTO)
                    && userValidationService.isDocNamesMatchSocialInfos(userName, resultResponseDTO);
        } catch (HttpClientErrorException e) {
            log.error("Error while analysing the document", e);
            return false;
        }
    }

}
