package com.simulation.notebook.fegin;

import com.simulation.notebook.api.PlantCategoryServerApi;
import com.simulation.notebook.server.ForestPlantServiceName;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 远程调用接口
 * @author Administrator
 */
@FeignClient(value = ForestPlantServiceName.FOREST_PLANT_SERVICE, contextId = PlantCategoryServerApi.CONTEXT_ID)
public interface PlantCategoryServerApiFeign extends PlantCategoryServerApi {

}
