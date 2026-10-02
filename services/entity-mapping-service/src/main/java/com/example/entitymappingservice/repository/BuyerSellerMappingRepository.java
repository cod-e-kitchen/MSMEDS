package com.example.entitymappingservice.repository;

import com.example.entitymappingservice.dto.BuyerSellerMappingRequest;
import com.example.entitymappingservice.dto.BuyerSellerMappingResponse;
import com.example.entitymappingservice.dto.PendingMappingApprovalResponse;
import com.example.entitymappingservice.dto.RejectMappingRequest;
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
    private final SimpleJdbcCall getPendingApprovalRequest;
    private final SimpleJdbcCall approveMappingRequest;
    private final SimpleJdbcCall rejectMappingRequest;

    public BuyerSellerMappingRepository(DataSource dataSource) {
        log.info("In BuyerSellerMappingRepository start...");
        this.createMappingRequest = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_CREATE_MAPPING_REQUEST")
                .withSchemaName("dbo")
                .returningResultSet("#result-set-1",
                        BeanPropertyRowMapper.newInstance(BuyerSellerMappingResponse.class));
        log.info("In BuyerSellerMappingRepository end...");

        this.getPendingApprovalRequest = new SimpleJdbcCall(dataSource)
                .withProcedureName("SP_GET_PENDING_MAPPING_REQUESTS")
                .withSchemaName("dbo")
                .returningResultSet("#result-set-1",
                        BeanPropertyRowMapper.newInstance(BuyerSellerMappingResponse.class));

        this.approveMappingRequest = new SimpleJdbcCall(dataSource)
                .withSchemaName("dbo")
                .withProcedureName("SP_APPROVE_MAPPING_REQUEST")
                .returningResultSet("#result-set-1",
                        BeanPropertyRowMapper.newInstance(BuyerSellerMappingResponse.class));

        this.rejectMappingRequest = new SimpleJdbcCall(dataSource)
                .withSchemaName("dbo")
                .withProcedureName("SP_REJECT_MAPPING_REQUEST")
                .returningResultSet("#result-set-1",
                        BeanPropertyRowMapper.newInstance(BuyerSellerMappingResponse.class));
    }

    public BuyerSellerMappingResponse raiseMappingRequest
            (BuyerSellerMappingRequest buyerSellerMappingRequest) {

        log.info("In BuyerSellerMappingRepository start...");

        log.info("Create Mapping Request");

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("buyer_entity_id",  buyerSellerMappingRequest.getBuyerEntityId())
                .addValue("seller_entity_id",  buyerSellerMappingRequest.getSellerEntityId())
                .addValue("created_by", "SYSTEM");

        log.info("Create Mapping Reqeust ==> sql parameter source: {}", sqlParameterSource);

        try{
            Map<String, Object> result = createMappingRequest.execute(sqlParameterSource);
            List<BuyerSellerMappingResponse> responseList = (List<BuyerSellerMappingResponse>) result.get("#result-set-1");
            log.info("sql result set: {}", responseList);
            return responseList.get(0);
        }catch (DataAccessException ex){
            SQLException sqlEx =
                    (SQLException) ex.getMostSpecificCause();
            log.info("Create Mapping Reqeust ==> sqlException:{}",sqlEx.getMessage());

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

    public List<PendingMappingApprovalResponse> getPendingApprovals(Long sellerEntityId) {
        log.info("In BuyerSellerMappingRepository start...");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("seller_entity_id",sellerEntityId);
        log.info("Pending Approval requests => sql parameter source: {}", sqlParameterSource);
        try{
            Map<String, Object> result = getPendingApprovalRequest.execute(sqlParameterSource);
            List<PendingMappingApprovalResponse> responseList = (List<PendingMappingApprovalResponse>) result.get("#result-set-1");
            log.info("Pending Approval requests => sql result set: {}", responseList);
            return responseList;
        }catch (DataAccessException ex){
            SQLException sqlEx =
                    (SQLException) ex.getMostSpecificCause();
            log.info("Pending Approval requests => sqlException:{}",sqlEx.getMessage());

            String message = switch (sqlEx.getErrorCode()) {
                case 50008 -> "INVALID_SELLER_ENTITY_ID";
                default -> "Invalid Entity Mapping";
            };
            throw new InvalidEntityException(message);
        }

    }

    public BuyerSellerMappingResponse approveMappingRequest(Long requestId, Long sellerEntityId) {
        log.info("In BuyerSellerMappingRepository start...");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("request_id", requestId)
                .addValue("seller_entity_id", sellerEntityId)
                .addValue("modified_by", sellerEntityId.toString());
        log.info("Approve Mapping Request ==> sql parameter source: {}", sqlParameterSource);
        try{
            Map<String, Object> result = approveMappingRequest.execute(sqlParameterSource);
            List<BuyerSellerMappingResponse> responseList = (List<BuyerSellerMappingResponse>) result.get("#result-set-1");
            log.info("Approve Mapping Request ==> sql result set: {}", responseList);
            return responseList.get(0);
        }catch (DataAccessException ex){
            SQLException sqlEx =
                    (SQLException) ex.getMostSpecificCause();
            log.info("Approve Mapping Request ==> sqlException:{}",sqlEx.getMessage());
            String message = switch (sqlEx.getErrorCode()){
                case 50009 -> "MAPPING_REQUEST_NOT_FOUND";
                case 50010 -> "ENTITIES_ALREADY_MAPPED";
                default -> "Invalid Entity Mapping";
            };
            throw new InvalidEntityException(message);
        }

    }

    public BuyerSellerMappingResponse rejectMappingRequest(Long requestId, Long sellerEntityId,  RejectMappingRequest rejectRequest) {
        log.info("In BuyerSellerMappingRepository start...");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("request_id", requestId)
                .addValue("seller_entity_id", sellerEntityId)
                .addValue("rejection_reason", rejectRequest.getRejectionReason())
                .addValue("modified_by", sellerEntityId.toString());
        log.info("Reject Mapping Request ==> sql parameter source: {}", sqlParameterSource);
        try{
            Map<String, Object> result = rejectMappingRequest.execute(sqlParameterSource);
            List<BuyerSellerMappingResponse> responseList = (List<BuyerSellerMappingResponse>) result.get("#result-set-1");
            log.info("Reject Mapping Request ==> sql result set: {}", responseList);
            return responseList.get(0);
        }catch (DataAccessException ex){
            SQLException sqlEx =
                    (SQLException) ex.getMostSpecificCause();
            log.info("Reject Mapping Request ==> sqlException:{}",sqlEx.getMessage());
            String message = switch(sqlEx.getErrorCode()){
                case 50011 -> "MAPPING_REQUEST_NOT_FOUND";
                case 50012 -> "REJECTION_REASON_IS_REQUIRED";
                default -> "Invalid Rejection Request";

            };
            throw new InvalidEntityException(message);
        }
    }

}
