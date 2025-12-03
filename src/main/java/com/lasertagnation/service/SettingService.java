package com.lasertagnation.service;

import com.lasertagnation.dto.PressMachineDto;
import com.lasertagnation.dto.ProductProcessDto;
import com.lasertagnation.dto.SettingDto;

import java.util.List;

public interface SettingService
{
    SettingDto save(SettingDto settingDto);
    List<SettingDto> getAll();
    SettingDto findById(Long id);
    SettingDto findByKey(String key);
    List<SettingDto> searchByKey(String key);

    String deleteById(Long id);
    SettingDto updateSetting(Long id, SettingDto settingDto);
}
