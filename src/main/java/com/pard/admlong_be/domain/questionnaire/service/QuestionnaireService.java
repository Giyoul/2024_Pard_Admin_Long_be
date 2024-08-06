package com.pard.admlong_be.domain.questionnaire.service;

import com.pard.admlong_be.domain.challenge.dto.response.ChallengeResponseDTO;
import com.pard.admlong_be.domain.challenge.entity.Challenge;
import com.pard.admlong_be.domain.questionnaire.dto.request.QuestionnaireRequestDTO;
import com.pard.admlong_be.domain.questionnaire.dto.response.QuestionnaireResponseDTO;
import com.pard.admlong_be.domain.questionnaire.repository.QuestionnaireRepository;
import com.pard.admlong_be.domain.storedQuestionnaire.repository.StoredQuestionnaireRepository;
import com.pard.admlong_be.domain.user.dto.response.UserResponseDTO;
import com.pard.admlong_be.domain.user.entity.User;
import com.pard.admlong_be.domain.user.repository.UserRepository;
import com.pard.admlong_be.domain.userChallengeRelation.entity.UserChallengeRelation;
import com.pard.admlong_be.global.responses.error.exceptions.ProjectException;
import com.pard.admlong_be.global.security.jwt.JWTUtil;
import com.pard.admlong_be.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final StoredQuestionnaireRepository storedQuestionnaireRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public ResponseDTO testWithoutLogin(QuestionnaireRequestDTO request) {
        try {
            // 필드 값을 검사하여 0이 아닌 값이 있는지 확인
            boolean hasNonZeroValue = request.getQuestion1() != 0 ||
                    request.getQuestion2() != 0 ||
                    request.getQuestion3() != 0 ||
                    request.getQuestion4() != 0 ||
                    request.getQuestion5() != 0 ||
                    request.getQuestion6() != 0 ||
                    request.getQuestion7() != 0 ||
                    request.getQuestion8() != 0 ||
                    request.getQuestion9() != 0 ||
                    request.getQuestion10() != 0 ||
                    request.getQuestion11() != 0;

            if (!hasNonZeroValue) {
                QuestionnaireResponseDTO.response responseDTO = new QuestionnaireResponseDTO.response(request);
                return new ResponseDTO(true, "문진 성공", responseDTO);
            } else {
                String reason1 = "";
                String reason2 = "";
                String reason3 = "";
                String reason4 = "";
                String reason5 = "";
                String reason6 = "";
                String reason7 = "";
                String reason8 = "";
                String reason9 = "";
                String reason10 = "";
                String reason11 = "";
                if (request.getQuestion1() != 0) {
                    reason1 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(2)).get((int)request.getQuestion1().longValue()).getQuestion_contents();
                }
                if (request.getQuestion2() != 0) {
                    reason2 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(3)).get((int)request.getQuestion2().longValue()).getQuestion_contents();
                }
                if (request.getQuestion3() != 0) {
                    reason3 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(4)).get((int)request.getQuestion3().longValue()).getQuestion_contents();
                }
                if (request.getQuestion4() != 0) {
                    reason4 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(5)).get((int)request.getQuestion4().longValue()).getQuestion_contents();
                }
                if (request.getQuestion5() != 0) {
                    reason5 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(6)).get((int)request.getQuestion5().longValue()).getQuestion_contents();
                }
                if (request.getQuestion6() != 0) {
                    reason6 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(7)).get((int)request.getQuestion6().longValue()).getQuestion_contents();
                }
                if (request.getQuestion7() != 0) {
                    reason7 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(8)).get((int)request.getQuestion7().longValue()).getQuestion_contents();
                }
                if (request.getQuestion8() != 0) {
                    reason8 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(9)).get((int)request.getQuestion8().longValue()).getQuestion_contents();
                }
                if (request.getQuestion9() != 0) {
                    reason9 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(10)).get((int)request.getQuestion9().longValue()).getQuestion_contents();
                }
                if (request.getQuestion10() != 0) {
                    reason10 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(11)).get((int)request.getQuestion10().longValue()).getQuestion_contents();
                }
                if (request.getQuestion11() != 0) {
                    reason11 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(12)).get((int)request.getQuestion11().longValue()).getQuestion_contents();
                }

                QuestionnaireResponseDTO.response responseDTO;

                Date lastDonationDate = request.getLast_donation_date();

                // Date를 LocalDate로 변환
                LocalDate lastDonationLocalDate = lastDonationDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                // 현재 날짜 가져오기
                LocalDate currentDate = LocalDate.now();

                // 두 날짜 사이의 차이 계산 (일 수)
                long daysDifference = ChronoUnit.DAYS.between(lastDonationLocalDate, currentDate);

                if (request.getDonation_type() == 0 && daysDifference < 62) {
                    String blood_type_reason = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(1)).get((int)request.getDonation_type()).getQuestion_contents();
                    responseDTO = new QuestionnaireResponseDTO.response(request, new QuestionnaireResponseDTO.reason(blood_type_reason, (int) (62-daysDifference), reason1, reason2, reason3, reason4, reason5, reason6, reason7, reason8, reason9, reason10, reason11));
                } else if (request.getDonation_type() == 1 && daysDifference < 14) {
                    String blood_type_reason = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(1)).get((int)request.getDonation_type()).getQuestion_contents();
                    responseDTO = new QuestionnaireResponseDTO.response(request, new QuestionnaireResponseDTO.reason(blood_type_reason, (int) (14-daysDifference), reason1, reason2, reason3, reason4, reason5, reason6, reason7, reason8, reason9, reason10, reason11));
                } else if (request.getDonation_type() == 2 && daysDifference < 14) {
                    String blood_type_reason = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(1)).get((int)request.getDonation_type()).getQuestion_contents();
                    responseDTO = new QuestionnaireResponseDTO.response(request, new QuestionnaireResponseDTO.reason(blood_type_reason, (int) (14-daysDifference), reason1, reason2, reason3, reason4, reason5, reason6, reason7, reason8, reason9, reason10, reason11));
                } else {
                    responseDTO = new QuestionnaireResponseDTO.response(request, new QuestionnaireResponseDTO.reason("", null, reason1, reason2, reason3, reason4, reason5, reason6, reason7, reason8, reason9, reason10, reason11));
                }

                return new ResponseDTO(true, "문진 성공", responseDTO);
            }
        } catch (Exception e) {
            return new ResponseDTO(false, "문진중에 오류가 발생했습니다. -> " + e.getMessage());
        }
    }

    public ResponseDTO testWithLogin(QuestionnaireRequestDTO request, String token) {
        try {
            if (!userRepository.existsByEmail(jwtUtil.getEmail(token))) {
                throw new ProjectException.UserNotExistException("없는 유저입니다.");
            }
            User user = userRepository.findByEmail(jwtUtil.getEmail(token)).orElseThrow(() -> new ProjectException.UserNotFoundException("해당 유저는 존재하나, Repository에서 불러오는 과정에서 문제가 발생했습니다."));
            // 필드 값을 검사하여 0이 아닌 값이 있는지 확인
            boolean hasNonZeroValue = request.getQuestion1() != 0 ||
                    request.getQuestion2() != 0 ||
                    request.getQuestion3() != 0 ||
                    request.getQuestion4() != 0 ||
                    request.getQuestion5() != 0 ||
                    request.getQuestion6() != 0 ||
                    request.getQuestion7() != 0 ||
                    request.getQuestion8() != 0 ||
                    request.getQuestion9() != 0 ||
                    request.getQuestion10() != 0 ||
                    request.getQuestion11() != 0;

            if (!hasNonZeroValue) {
                QuestionnaireResponseDTO.response responseDTO = new QuestionnaireResponseDTO.response(request);
                return new ResponseDTO(true, "문진 성공", responseDTO);
            } else {
                String reason1 = "";
                String reason2 = "";
                String reason3 = "";
                String reason4 = "";
                String reason5 = "";
                String reason6 = "";
                String reason7 = "";
                String reason8 = "";
                String reason9 = "";
                String reason10 = "";
                String reason11 = "";
                if (request.getQuestion1() != 0) {
                    reason1 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(2)).get((int)request.getQuestion1().longValue()).getQuestion_contents();
                }
                if (request.getQuestion2() != 0) {
                    reason2 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(3)).get((int)request.getQuestion2().longValue()).getQuestion_contents();
                }
                if (request.getQuestion3() != 0) {
                    reason3 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(4)).get((int)request.getQuestion3().longValue()).getQuestion_contents();
                }
                if (request.getQuestion4() != 0) {
                    reason4 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(5)).get((int)request.getQuestion4().longValue()).getQuestion_contents();
                }
                if (request.getQuestion5() != 0) {
                    reason5 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(6)).get((int)request.getQuestion5().longValue()).getQuestion_contents();
                }
                if (request.getQuestion6() != 0) {
                    reason6 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(7)).get((int)request.getQuestion6().longValue()).getQuestion_contents();
                }
                if (request.getQuestion7() != 0) {
                    reason7 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(8)).get((int)request.getQuestion7().longValue()).getQuestion_contents();
                }
                if (request.getQuestion8() != 0) {
                    reason8 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(9)).get((int)request.getQuestion8().longValue()).getQuestion_contents();
                }
                if (request.getQuestion9() != 0) {
                    reason9 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(10)).get((int)request.getQuestion9().longValue()).getQuestion_contents();
                }
                if (request.getQuestion10() != 0) {
                    reason10 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(11)).get((int)request.getQuestion10().longValue()).getQuestion_contents();
                }
                if (request.getQuestion11() != 0) {
                    reason11 = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(12)).get((int)request.getQuestion11().longValue()).getQuestion_contents();
                }

                QuestionnaireResponseDTO.response responseDTO;

                Date lastDonationDate = request.getLast_donation_date();

                // Date를 LocalDate로 변환
                LocalDate lastDonationLocalDate = lastDonationDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                // 현재 날짜 가져오기
                LocalDate currentDate = LocalDate.now();

                // 두 날짜 사이의 차이 계산 (일 수)
                long daysDifference = ChronoUnit.DAYS.between(lastDonationLocalDate, currentDate);

                if (request.getDonation_type() == 0 && daysDifference < 62) {
                    String blood_type_reason = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(1)).get((int)request.getDonation_type()).getQuestion_contents();
                    responseDTO = new QuestionnaireResponseDTO.response(request, new QuestionnaireResponseDTO.reason(blood_type_reason, (int) (62-daysDifference), reason1, reason2, reason3, reason4, reason5, reason6, reason7, reason8, reason9, reason10, reason11));
                } else if (request.getDonation_type() == 1 && daysDifference < 14) {
                    String blood_type_reason = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(1)).get((int)request.getDonation_type()).getQuestion_contents();
                    responseDTO = new QuestionnaireResponseDTO.response(request, new QuestionnaireResponseDTO.reason(blood_type_reason, (int) (14-daysDifference), reason1, reason2, reason3, reason4, reason5, reason6, reason7, reason8, reason9, reason10, reason11));
                } else if (request.getDonation_type() == 2 && daysDifference < 14) {
                    String blood_type_reason = storedQuestionnaireRepository.findAllByQuestionNum(Long.valueOf(1)).get((int)request.getDonation_type()).getQuestion_contents();
                    responseDTO = new QuestionnaireResponseDTO.response(request, new QuestionnaireResponseDTO.reason(blood_type_reason, (int) (14-daysDifference), reason1, reason2, reason3, reason4, reason5, reason6, reason7, reason8, reason9, reason10, reason11));
                } else {
                    responseDTO = new QuestionnaireResponseDTO.response(request, new QuestionnaireResponseDTO.reason("", null, reason1, reason2, reason3, reason4, reason5, reason6, reason7, reason8, reason9, reason10, reason11));
                }

                return new ResponseDTO(true, "문진 성공", responseDTO);
            }

        } catch (ProjectException.UserNotFoundException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (ProjectException.UserNotExistException e) {
            return new ResponseDTO(false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(false, "문진중에 오류가 발생했습니다. -> " + e.getMessage());
        }
    }
}
