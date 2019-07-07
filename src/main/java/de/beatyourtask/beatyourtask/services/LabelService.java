package de.beatyourtask.beatyourtask.services;


import de.beatyourtask.beatyourtask.model.Label;
import de.beatyourtask.beatyourtask.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    /**
     * saves a label in the database
     * @param label project to be saved
     * @return
     */
    public Label saveLabel(Label label) { return labelRepository.save(label); }

    /**
     *
     * @return all labels saved in the database
     */
    @Transactional
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    /**
     * gets an id and returns the respective label
     * @param id
     * @return
     */
    @Transactional
    public Label getLabelById(Integer id) { return labelRepository.getOne(id); }
}
