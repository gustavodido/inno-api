package inno.edu.api.support;

import inno.edu.api.domain.appointment.feedback.commands.dtos.CreateFeedbackRequest;
import inno.edu.api.domain.appointment.root.models.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.dtos.UpdateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.dtos.UpdateAppointmentStatusRequest;
import inno.edu.api.domain.attachment.models.dtos.UpdateAttachmentRequest;
import inno.edu.api.domain.availability.models.dtos.CreateAvailabilityByMentorIdRequest;
import inno.edu.api.domain.availability.models.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.models.dtos.UpdateAvailabilityRequest;
import inno.edu.api.domain.profile.accomplishment.models.dtos.CreateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.models.dtos.UpdateAccomplishmentRequest;
import inno.edu.api.domain.profile.experience.models.dtos.CreateExperienceRequest;
import inno.edu.api.domain.profile.experience.models.dtos.UpdateExperienceRequest;
import inno.edu.api.domain.profile.interest.models.dtos.CreateInterestRequest;
import inno.edu.api.domain.profile.interest.models.dtos.UpdateInterestRequest;
import inno.edu.api.domain.profile.root.models.dtos.ApproveProfileAssociationRequest;
import inno.edu.api.domain.profile.root.models.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.root.models.dtos.ProfileAssociationRequest;
import inno.edu.api.domain.profile.root.models.dtos.RejectProfileAssociationRequest;
import inno.edu.api.domain.profile.root.models.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.service.models.dtos.CreateServiceRequest;
import inno.edu.api.domain.profile.service.models.dtos.UpdateServiceRequest;
import inno.edu.api.domain.school.root.models.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.root.models.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.skill.models.dtos.CreateSkillRequest;
import inno.edu.api.domain.skill.models.dtos.UpdateSkillRequest;
import inno.edu.api.domain.user.root.models.dtos.CreateUserRequest;
import inno.edu.api.domain.user.root.models.dtos.LoginRequest;
import inno.edu.api.domain.user.root.models.dtos.UpdateUserRequest;
import inno.edu.api.domain.user.transaction.models.dtos.CreateTransactionRequest;
import inno.edu.api.domain.user.transaction.models.dtos.UpdateTransactionRequest;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

import static java.lang.String.format;

public class Payloads {
    private static String loadPayload(String fileName) {
        String result = "";

        ClassLoader classLoader = Payloads.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String loginUserPayload(LoginRequest credentials) {
        return format(loadPayload("payloads/user/login-user.json"), credentials.getUsername(), credentials.getPassword());
    }

    public static String postUserPayload(CreateUserRequest createUserRequest) {
        return format(loadPayload("payloads/user/post-user.json"), createUserRequest.getFirstName(), createUserRequest.getLastName(), createUserRequest.getUsername(), createUserRequest.getEmail(), createUserRequest.getPassword(), createUserRequest.getConfirmPassword(), createUserRequest.getDeviceId());
    }

    public static String putUserPayload(UpdateUserRequest updateUserRequest) {
        return format(loadPayload("payloads/user/put-user.json"), updateUserRequest.getFirstName(), updateUserRequest.getLastName(), updateUserRequest.getEmail(), updateUserRequest.getDeviceId());
    }

    public static String postTransactionPayload(CreateTransactionRequest createTransactionRequest) {
        return format(loadPayload("payloads/user/transaction/post-transaction.json"), createTransactionRequest.getAppointmentId(), createTransactionRequest.getValue(), createTransactionRequest.getType());
    }

    public static String putTransactionPayload(UpdateTransactionRequest updateTransactionRequest) {
        return format(loadPayload("payloads/user/transaction/put-transaction.json"), updateTransactionRequest.getValue(), updateTransactionRequest.getType());
    }

    public static String postProfilePayload(CreateProfileRequest createProfileRequest) {
        return format(loadPayload("payloads/profile/post-profile.json"), createProfileRequest.getUserId(), createProfileRequest.getDescription(), createProfileRequest.getLocation(), createProfileRequest.getCompany(), createProfileRequest.getProfileReferenceId());
    }

    public static String putProfilePayload(UpdateProfileRequest updateProfileRequest) {
        return format(loadPayload("payloads/profile/put-profile.json"), updateProfileRequest.getDescription(), updateProfileRequest.getLocation(), updateProfileRequest.getProfileReferenceId());
    }

    public static String approveProfileAssociationPayload(ApproveProfileAssociationRequest approveProfileAssociationRequest) {
        return format(loadPayload("payloads/profile/association/approve-profile-association.json"), approveProfileAssociationRequest.getRate());
    }

    public static String rejectProfileAssociationPayload(RejectProfileAssociationRequest rejectProfileAssociationRequest) {
        return format(loadPayload("payloads/profile/association/reject-profile-association.json"), rejectProfileAssociationRequest.getDescription());
    }

    public static String associateProfilePayload(ProfileAssociationRequest profileAssociationRequest) {
        return format(loadPayload("payloads/profile/association/associate-profile.json"), profileAssociationRequest.getSchoolId());
    }

    public static String postExperiencePayload(CreateExperienceRequest createExperienceRequest) {
        return format(loadPayload("payloads/profile/experience/post-experience.json"), createExperienceRequest.getTitle(), createExperienceRequest.getInstitution(), createExperienceRequest.getLocation(), createExperienceRequest.getArea(), createExperienceRequest.getFromDate(), createExperienceRequest.getToDate(), createExperienceRequest.getDescription(), createExperienceRequest.getType());
    }

    public static String putExperiencePayload(UpdateExperienceRequest updateExperienceRequest) {
        return format(loadPayload("payloads/profile/experience/put-experience.json"), updateExperienceRequest.getTitle(), updateExperienceRequest.getInstitution(), updateExperienceRequest.getLocation(), updateExperienceRequest.getArea(), updateExperienceRequest.getFromDate(), updateExperienceRequest.getToDate(), updateExperienceRequest.getDescription());
    }

    public static String postInterestPayload(CreateInterestRequest createInterestRequest) {
        return format(loadPayload("payloads/profile/interest/post-interest.json"), createInterestRequest.getTitle(), createInterestRequest.getDescription());
    }

    public static String putInterestPayload(UpdateInterestRequest updateInterestRequest) {
        return format(loadPayload("payloads/profile/interest/put-interest.json"), updateInterestRequest.getTitle(), updateInterestRequest.getDescription());
    }

    public static String postAccomplishmentPayload(CreateAccomplishmentRequest createAccomplishmentRequest) {
        return format(loadPayload("payloads/profile/accomplishment/post-accomplishment.json"), createAccomplishmentRequest.getTitle(), createAccomplishmentRequest.getDescription(), createAccomplishmentRequest.getType());
    }

    public static String putAccomplishmentPayload(UpdateAccomplishmentRequest updateAccomplishmentRequest) {
        return format(loadPayload("payloads/profile/accomplishment/put-accomplishment.json"), updateAccomplishmentRequest.getTitle(), updateAccomplishmentRequest.getDescription());
    }

    public static String postServicePayload(CreateServiceRequest createServiceRequest) {
        return format(loadPayload("payloads/profile/service/post-service.json"), createServiceRequest.getTitle(), createServiceRequest.getDescription());
    }

    public static String putServicePayload(UpdateServiceRequest updateServiceRequest) {
        return format(loadPayload("payloads/profile/service/put-service.json"), updateServiceRequest.getTitle(), updateServiceRequest.getDescription());
    }

    public static String postSchoolPayload(CreateSchoolRequest createSchoolRequest) {
        return format(loadPayload("payloads/school/post-school.json"), createSchoolRequest.getName(), createSchoolRequest.getDescription());
    }

    public static String putSchoolPayload(UpdateSchoolRequest updateSchoolRequest) {
        return format(loadPayload("payloads/school/put-school.json"), updateSchoolRequest.getName(), updateSchoolRequest.getDescription());
    }

    public static String postSkillPayload(CreateSkillRequest createSkillRequest) {
        return format(loadPayload("payloads/skill/post-skill.json"), createSkillRequest.getTitle(), createSkillRequest.getDescription());
    }

    public static String putSkillPayload(UpdateSkillRequest updateSkillRequest) {
        return format(loadPayload("payloads/skill/put-skill.json"), updateSkillRequest.getTitle(), updateSkillRequest.getDescription());
    }

    public static String postAvailabilityPayload(CreateAvailabilityRequest createAvailabilityRequest) {
        return format(loadPayload("payloads/availability/post-availability.json"), createAvailabilityRequest.getMentorProfileId(), createAvailabilityRequest.getFromDateTime(), createAvailabilityRequest.getToDateTime());
    }

    public static String postAvailabilityByMentorPayload(CreateAvailabilityByMentorIdRequest createAvailabilityByMentorIdRequest) {
        return format(loadPayload("payloads/availability/post-availability-by-mentor.json"), createAvailabilityByMentorIdRequest.getFromDateTime(), createAvailabilityByMentorIdRequest.getToDateTime());
    }

    public static String putAvailabilityPayload(UpdateAvailabilityRequest updateAvailabilityRequest) {
        return format(loadPayload("payloads/availability/put-availability.json"), updateAvailabilityRequest.getFromDateTime(), updateAvailabilityRequest.getToDateTime());
    }

    public static String postAppointmentPayload(CreateAppointmentRequest createAppointmentRequest) {
        return format(loadPayload("payloads/appointment/post-appointment.json"), createAppointmentRequest.getMentorProfileId(), createAppointmentRequest.getMenteeProfileId(), createAppointmentRequest.getFromDateTime(), createAppointmentRequest.getToDateTime(), createAppointmentRequest.getDescription());
    }

    public static String putAppointmentPayload(UpdateAppointmentRequest appointmentRequest) {
        return format(loadPayload("payloads/appointment/put-appointment.json"), appointmentRequest.getFromDateTime(), appointmentRequest.getToDateTime(), appointmentRequest.getDescription());
    }

    public static String putAppointmentStatusPayload(UpdateAppointmentStatusRequest updateAppointmentStatusRequest) {
        return format(loadPayload("payloads/appointment/put-appointment-status.json"), updateAppointmentStatusRequest.getReason(), updateAppointmentStatusRequest.getStatus());
    }

    public static String postFeedbackPayload(CreateFeedbackRequest createFeedbackRequest) {
        return format(loadPayload("payloads/appointment/post-feedback.json"), createFeedbackRequest.getSource(), createFeedbackRequest.getRating(), createFeedbackRequest.getDescription());
    }

    public static String putAttachmentPayload(UpdateAttachmentRequest updateAttachmentRequest) {
        return format(loadPayload("payloads/attachment/put-attachment.json"), updateAttachmentRequest.getDescription());
    }
}
