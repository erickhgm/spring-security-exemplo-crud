package com.ehgm.vinhos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehgm.vinhos.model.Vinho;

public interface Vinhos extends JpaRepository<Vinho, Long> {

	public List<Vinho> findByNomeContainingIgnoreCase(String nome);

}
