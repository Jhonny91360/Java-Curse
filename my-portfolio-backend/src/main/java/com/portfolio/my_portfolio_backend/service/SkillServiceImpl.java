package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.model.Skill;
import com.portfolio.my_portfolio_backend.repository.ISkillRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements ISkillService{

    private final ISkillRepository skillRepository;

    @Override
    public Skill save(Skill skill) {
        if(skill.getLevelPercentage()<0 || skill.getLevelPercentage()>100){
            throw new IllegalArgumentException("El porcetanje debe estar entre 0 y 100");
        }
        return skillRepository.save(skill);
    }

    @Override
    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }

    @Override
    public List<Skill> findByPersonalInfoId(Long personalInfoId) {
        return skillRepository.findByPersonalInfoId(personalInfoId);
    }
}
