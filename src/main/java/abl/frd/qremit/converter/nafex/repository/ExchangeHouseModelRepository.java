package abl.frd.qremit.converter.nafex.repository;

import abl.frd.qremit.converter.nafex.model.ExchangeHouseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExchangeHouseModelRepository extends JpaRepository<ExchangeHouseModel, Integer> {

    @Query("SELECT n FROM ExchangeHouseModel n WHERE n.isActive = '0'")
    List<ExchangeHouseModel> findAllInactiveExchangeHouse();
    @Query("SELECT n FROM ExchangeHouseModel n WHERE n.isActive = '1'")
    List<ExchangeHouseModel> findAllActiveExchangeHouseList();
    @Transactional
    @Modifying
    @Query("UPDATE ExchangeHouseModel n SET n.isActive = '1' WHERE n.Id = :id")
    void setExchangeHouseActiveStatusTrueById(int id);
    @Transactional
    @Modifying
    @Query("UPDATE ExchangeHouseModel n SET n.isActive = '0' WHERE n.Id = :id")
    void setExchangeHouseActiveStatusFalseById(int id);

}
