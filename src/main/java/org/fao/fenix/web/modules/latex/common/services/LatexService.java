/**
 *
 * FENIX (Food security and Early warning Network and Information Exchange)
 *
 * Copyright (c) 2008, by FAO of UN under the EC-FAO Food Security
Information for Action Programme
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.fao.fenix.web.modules.latex.common.services;

import java.util.List;
import java.util.Map;

import org.fao.fenix.web.modules.core.common.exception.FenixGWTException;
import org.fao.fenix.web.modules.designer.common.vo.DesignerVO;
import org.fao.fenix.web.modules.olap.common.vo.ResourceViewVO;

import com.google.gwt.user.client.rpc.RemoteService;

public interface LatexService extends RemoteService {

	public Long save(Map<String, List<ResourceViewVO>> latexReportParameters) throws FenixGWTException;
	
	public Map<String, List<ResourceViewVO>> load(Long reportID) throws FenixGWTException;
	
	public String exportPDF(String latexAreaContent) throws FenixGWTException;
	
	public String exportPDF(DesignerVO vo, String JSESSIONID) throws FenixGWTException;
	
	public String exportPDF(Long reportID, String language) throws FenixGWTException;
	
}