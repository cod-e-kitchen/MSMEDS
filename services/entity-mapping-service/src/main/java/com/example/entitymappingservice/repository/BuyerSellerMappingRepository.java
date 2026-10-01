package com.example.entitymappingservice.repository;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;
import com.example.entitymappingservice.exception.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class BuyerSellerMappingRepository {

    private final SimpleJdbcCall createMappingRequest;


    public BuyerSellerMappingRepository(DataSource dataSource) {
        log.info("In BuyerSellerMappingRepository start...");
        this.createMappingRequest = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_CREATE_MAPPING_REQUEST")
                .withSchemaName("dbo")
                .returningResultSet("#result-set-1",
                        BeanPropertyRowMapper.newInstance(BuyerSellerMappingResponse.class));
        log.info("In BuyerSellerMappingRepository end...");
    }

    public BuyerSellerMappingResponse raiseMappingRequest
            (BuyerSellerMappingRequest buyerSellerMappingRequest) {

        log.info("In BuyerSellerMappingRepository start...");

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("buyer_entity_id",  buyerSellerMappingRequest.getBuyerEntityId())
                .addValue("seller_entity_id",  buyerSellerMappingRequest.getSellerEntityId())
                .addValue("created_by", "SYSTEM");

        log.info("In BuyerSellerMappingRepository end with sql parameter source: {}", sqlParameterSource);

        try{
            Map<String, Object> result = createMappingRequest.execute(sqlParameterSource);
            List<BuyerSellerMappingResponse> responseList = (List<BuyerSellerMappingResponse>) result.get("#result-set-1");
            log.info("sql result set: {}", responseList);
            return responseList.get(0);
        }catch (DataAccessException ex){
            SQLException sqlEx =
                    (SQLException) ex.getMostSpecificCause();
            log.info("sqlException:{}",sqlEx.getMessage());

            String message = switch (sqlEx.getErrorCode()) {
                case 50001 -> "BUYER_ENTITY_DOES_NOT_EXIST";
                case 50002 -> "ENTITY_IS_NOT_BUYER";
                case 50003 -> "SELLER_ENTITY_DOES_NOT_EXIST";
                case 50004 -> "ENTITY_IS_NOT_SELLER";
                case 50005 -> "SAME_ENTITY_ID_PROVIDED";
                case 50006 -> "ENTITIES_ALREADY_MAPPED";
                case 50007 -> "MAPPING_REQUEST_ALREADY_EXISTS";
                default -> "Invalid Entity Mapping";
            };
            throw new InvalidEntityException(message);
        }

    }

}
