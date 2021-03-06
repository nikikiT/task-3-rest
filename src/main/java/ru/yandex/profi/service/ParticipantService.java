package ru.yandex.profi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.yandex.profi.entity.Participant;
import ru.yandex.profi.entity.Promo;
import ru.yandex.profi.repository.ParticipantRepository;
import ru.yandex.profi.repository.PromoActionRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class ParticipantService {
    PromoActionRepository promoRepository;
    ParticipantRepository participantRepository;

    public List<Participant> get(long promoId){
        Promo action = promoRepository.findById(promoId).orElseThrow(EntityNotFoundException::new);
        return participantRepository.findAllByAction(action);
    }



    public Participant get(long promoId, long id){
        Promo action = promoRepository.findById(promoId).orElseThrow(EntityNotFoundException::new);
        return participantRepository.findByActionAndId(action,id).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(Long promoId,Long id){
        Promo action = promoRepository.findById(promoId).orElseThrow(EntityNotFoundException::new);
        Participant participant = participantRepository.findByActionAndId(action,id).orElseThrow(EntityNotFoundException::new);
        participantRepository.delete(participant);
    }
    public Long save(Long promoId,Participant data){
        Promo action = promoRepository.findById(promoId).orElseThrow(EntityNotFoundException::new);
        Participant participant = participantRepository.save(new Participant(0L,data.getName(), action));
        return participant.getId();
    }

    public void update(Long promoId,Long id,@RequestBody Participant data){
        Promo action = promoRepository.findById(promoId).orElseThrow(EntityNotFoundException::new);
        Participant participant = participantRepository.findByActionAndId(action,id).orElseThrow(EntityNotFoundException::new);
        participant.setName(data.getName());
        participantRepository.save(participant);
    }
}
