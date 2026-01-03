package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.model.PersonalInfo;
import com.portfolio.my_portfolio_backend.repository.IPersonalInforRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalInfoServiceImpl implements IPersonalInfoService{

    private final IPersonalInforRepository personalInforRepository;

    public PersonalInfoServiceImpl(IPersonalInforRepository personalInforRepository){
        this.personalInforRepository = personalInforRepository;
    }

    @Override
    public PersonalInfo save(PersonalInfo personalInfo) {
        return personalInforRepository.save(personalInfo);
    }

    @Override
    public Optional<PersonalInfo> findById(Long id) {
        return personalInforRepository.findById(id);
    }

    @Override
    public List<PersonalInfo> findAll() {
        return personalInforRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        personalInforRepository.deleteById(id);
    }
}
