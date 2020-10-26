package com.lyz.service;

import com.lyz.dao.TagsRepository;
import com.lyz.lyzException.NotFoundException;
import com.lyz.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagsService{

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagsRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagsRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagsRepository.findTagByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagsRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagsRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return tagsRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagsRepository.findAllById(converToList(ids));
    }

    private List<Long> converToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for(int i=0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag one = tagsRepository.getOne(id);
        if(one == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,one);

        return tagsRepository.save(one);

    }

    @Override
    public void deleteTag(Long id) {
        tagsRepository.deleteById(id);
    }
}
