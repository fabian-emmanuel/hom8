package org.indulge.hom8.mappers;

import org.indulge.hom8.dtos.UserProfileDTO;
import org.indulge.hom8.dtos.UserRequestDTO;
import org.indulge.hom8.dtos.UserResponseDTO;
import org.indulge.hom8.enums.UserType;
import org.indulge.hom8.models.Helper;
import org.indulge.hom8.models.HomeOwner;
import org.indulge.hom8.models.User;
import org.indulge.hom8.utils.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    @Mapping(target = "preferences", ignore = true)
    @Mapping(target = "pin", expression = "java(encoder.encode(requestDTO.pin()))")
    HomeOwner toHomeOwner(UserRequestDTO requestDTO, PasswordEncoder encoder, UserType userType);


    @Mapping(target = "createdAt", source = "user.createdAt", qualifiedByName = "formatToString")
    UserResponseDTO toUserResponseDTO(User user);

    @Named("formatToString")
    default String formatToString(LocalDateTime dateTime) {
        return DateUtil.formatLocalDateTimeToString(dateTime);
    }

    @Mapping(target = "pin", expression = "java(encoder.encode(requestDTO.pin()))")
    Helper toHelper(UserRequestDTO requestDTO, PasswordEncoder encoder, UserType userType);

    UserProfileDTO userProfile(Helper helper);
    UserProfileDTO userProfile(HomeOwner homeOwner);
}
