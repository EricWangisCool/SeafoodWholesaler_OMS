package tw.com.ispan.eeit48.mainfunction.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.mainfunction.model.table.CompanyFollowingList;

@Repository
public interface CompanyFollowingListRepository extends JpaRepository<CompanyFollowingList, Integer> {
	
	List<CompanyFollowingList> findAllByBuyerId(int buyerId);
	
}
