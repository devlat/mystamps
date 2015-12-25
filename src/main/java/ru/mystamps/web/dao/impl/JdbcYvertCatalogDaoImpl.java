/*
 * Copyright (C) 2009-2015 Slava Semushin <slava.semushin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package ru.mystamps.web.dao.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.mystamps.web.dao.YvertCatalogDao;

public class JdbcYvertCatalogDaoImpl extends JdbcCatalogDao implements YvertCatalogDao {
	
	@Value("${yvert.create}")
	private String addYvertNumberSql;
	
	@Value("${series_yvert.add}")
	private String addYvertNumbersToSeriesSql;
	
	public JdbcYvertCatalogDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}
	
	@Override
	public void add(Set<String> yvertNumbers) {
		add(yvertNumbers, addYvertNumberSql);
	}
	
	@Override
	public void addToSeries(Integer seriesId, Set<String> yvertNumbers) {
		addToSeries(seriesId, yvertNumbers, addYvertNumbersToSeriesSql);
	}
	
}
