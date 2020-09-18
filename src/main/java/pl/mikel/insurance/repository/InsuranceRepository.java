package pl.mikel.insurance.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.mikel.insurance.dao.InsuranceDao;
import java.util.List;

@Repository
public interface InsuranceRepository extends PagingAndSortingRepository<InsuranceDao,Long> {
    List<InsuranceDao> findAll();
    List<InsuranceDao> findAllById(Long id);
    List<InsuranceDao> findAllByIdAndActualUser(Long id, String userEmail);
    List<InsuranceDao> findFirstByActualUserOrderByIdDesc(String actualUser);
    List<InsuranceDao> findAllByActualUser(String actualUser);
    List<InsuranceDao> deleteAllByIdAndActualUser(Long id, String actualUser);
    Page<InsuranceDao> findAllByActualUser(String actualUsers, Pageable pageable);
    Integer countAllByActualUser(String actualUser);


}
