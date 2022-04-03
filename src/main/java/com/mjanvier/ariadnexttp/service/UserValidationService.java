package com.mjanvier.ariadnexttp.service;

import com.mjanvier.ariadnexttp.idcheck.ControlDTO;
import com.mjanvier.ariadnexttp.idcheck.HolderDetailDTO;
import com.mjanvier.ariadnexttp.idcheck.ResultResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserValidationService {

    public boolean isDocNamesMatchSocialInfos(String currentUserFullName, ResultResponseDTO resultResponseDTO) {
        final String[] nameParts = currentUserFullName.split(" ");
        final HolderDetailDTO holderDetail = resultResponseDTO.getHolderDetail();
        // to uppercase may be useless
        final List<String> docFirstNames = holderDetail.getFirstName().stream()
                .map(String::toUpperCase).collect(Collectors.toList());
        final List<String> docLastNames = holderDetail.getLastName().stream()
                .map(String::toUpperCase).collect(Collectors.toList());

        final boolean docNamesMatchSocialInfos = Arrays.stream(nameParts)
                .map(String::toUpperCase)
                .allMatch(namePart -> docFirstNames.contains(namePart) || docLastNames.contains(namePart));
        log.debug("name matching result is  {} for user full name {} and resultResponse {}", docNamesMatchSocialInfos,
                currentUserFullName, resultResponseDTO);
        return docNamesMatchSocialInfos;
    }

    public boolean isAllIdChecksAreOk(ResultResponseDTO resultResponseDTO) {
        final boolean allIdChecksAreOk = resultResponseDTO.getCheckReportSummary().getCheck()
                .stream().map(ControlDTO::getResult)
                .noneMatch(result -> ControlDTO.ResultEnum.ERROR.equals(result) || ControlDTO.ResultEnum.WARNING.equals(result));
        log.debug("idChecks are {} for resultResponse {}", allIdChecksAreOk, resultResponseDTO);
        return allIdChecksAreOk;
    }
}
