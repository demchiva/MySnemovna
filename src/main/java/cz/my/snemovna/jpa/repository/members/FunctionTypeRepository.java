package cz.my.snemovna.jpa.repository.members;

import cz.my.snemovna.jpa.model.members.FunctionType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The function type repository.
 */
public interface FunctionTypeRepository extends JpaRepository<FunctionType, Long> {
}
