package com.sqa.bhyt.common.base;

import com.sqa.bhyt.common.dto.request.DeleteRequest;
import com.sqa.bhyt.common.dto.response.ServiceResponse;

public interface BaseService<T, S> {
	ServiceResponse<T> save(S req);

	ServiceResponse<T> update(S req);

	ServiceResponse<String> delete(DeleteRequest listId);
}
