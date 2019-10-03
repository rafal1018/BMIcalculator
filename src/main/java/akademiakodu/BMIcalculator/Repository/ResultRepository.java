package akademiakodu.BMIcalculator.Repository;

import akademiakodu.BMIcalculator.Model.Result;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Primary
@Repository("resultRepository")
public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findAllById(Integer id);
}
