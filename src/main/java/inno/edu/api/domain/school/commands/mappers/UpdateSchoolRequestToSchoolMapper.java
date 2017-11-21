package inno.edu.api.domain.school.commands.mappers;

import inno.edu.api.domain.school.commands.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.school.models.School;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateSchoolRequestToSchoolMapper {
    void updateSchoolRequestToSchool(UpdateSchoolRequest updateSchoolRequest, @MappingTarget School school);
}