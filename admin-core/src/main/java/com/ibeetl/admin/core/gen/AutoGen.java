package com.ibeetl.admin.core.gen;

import com.ibeetl.admin.core.gen.model.Entity;

public interface AutoGen {
	public void make(Target target, Entity entity);
	public String getName();
}
