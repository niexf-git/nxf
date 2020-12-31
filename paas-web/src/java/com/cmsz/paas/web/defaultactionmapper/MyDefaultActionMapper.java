package com.cmsz.paas.web.defaultactionmapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;
import org.apache.struts2.dispatcher.mapper.ParameterAction;

public class MyDefaultActionMapper extends DefaultActionMapper {

	public void handleSpecialParameters(HttpServletRequest request,
			ActionMapping mapping) {
		Set uniqueParameters = new HashSet();
		Map parameterMap = request.getParameterMap();
		for (Iterator iterator = parameterMap.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();

			if ((key.endsWith(".x")) || (key.endsWith(".y"))) {
				key = key.substring(0, key.length() - 2);
			}

			// -- jason.zhou 20130708 add start -- //
			if ((key.contains("redirect:"))
					|| (key.contains("redirectAction:"))
					|| (key.contains("action:"))) {
				return;
			}
			// -- jason.zhou 20130708 add end -- //

			if (!uniqueParameters.contains(key)) {
				ParameterAction parameterAction = (ParameterAction) this.prefixTrie
						.get(key);
				if (parameterAction != null) {
					parameterAction.execute(key, mapping);
					uniqueParameters.add(key);
					break;
				}
			}
		}
	}

}
