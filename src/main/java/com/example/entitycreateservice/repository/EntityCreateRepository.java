package com.example.entitycreateservice.repository;

import com.example.entitycreateservice.dto.EntityRequestDto;
import com.example.entitycreateservice.dto.EntityResponseDto;
import com.example.entitycreateservice.exception.EntityValidationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class EntityCreateRepository {


    private final SimpleJdbcCall simpleJdbcCall;
    public EntityCreateRepository(JdbcTemplate jdbcTemplate) {
        log.info("Inside EntityCreateRepository");
        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_create_entity")
                .returningResultSet("#result-set-1",
                        BeanPropertyRowMapper.newInstance(EntityResponseDto.class));
    }
    public EntityResponseDto addEntityToDs(EntityRequestDto entityRequestDto){
        log.info("Inside addEntityToDs");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("entity_name",entityRequestDto.getEntity_name())
                .addValue("entity_location_city",entityRequestDto.getEntity_location_city())
                .addValue("entity_location_state",entityRequestDto.getEntity_location_state())
                .addValue("entity_location_country",entityRequestDto.getEntity_location_country())
                .addValue("entity_type",entityRequestDto.getEntity_type())
                .addValue("created_by","SYSTEM");
        log.info("entityRequestDto:{}",sqlParameterSource);
        try{
            log.info("Starting simpleJdbcCall");
            Map<String, Object> result = simpleJdbcCall.execute(sqlParameterSource);
            log.info("result:{}",result);
            List<EntityResponseDto> resultList = (List<EntityResponseDto>) result.get("#result-set-1");
            log.info("entityResponseDto:{}",resultList);
            return resultList.get(0);

        }catch (DataAccessException ex){
            SQLException sqlEx =
                    (SQLException) ex.getMostSpecificCause();
            log.info("sqlException:{}",sqlEx.getMessage());

            String message = switch (sqlEx.getErrorCode()) {
                case 50001 -> "ENTITY_NAME_REQUIRED";
                case 50002 -> "ENTITY_CITY_REQUIRED";
                case 50003 -> "ENTITY_STATE_REQUIRED";
                case 50004 -> "ENTITY_COUNTRY_REQUIRED";
                case 50005 -> "INVALID_ENTITY_TYPE";
                case 50006 -> "CREATED_BY_REQUIRED";
                case 50017 -> "ENTITY_ALREADY_EXISTS";
                default -> "Invalid Entity Creation";
            };
            throw new EntityValidationException(message);
        }

    }

}
