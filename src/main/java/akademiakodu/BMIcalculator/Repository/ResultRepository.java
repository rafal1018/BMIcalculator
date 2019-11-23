package akademiakodu.BMIcalculator.Repository;

import akademiakodu.BMIcalculator.Model.Result;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Primary
@Repository("resultRepository")
public interface ResultRepository extends CrudRepository<Result, Integer> {

}
