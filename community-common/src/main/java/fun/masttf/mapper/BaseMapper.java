package fun.masttf.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T, P> {

    /*
     * insert插入数据
     */
    Integer insert(@Param("bean") T t);

    /*
     * insert update 插入或者更新数据
     */
    Integer insertOrUpdate(@Param("bean") T t);

    /*
     * insertBatch 批量插入数据
     */
    Integer insertBatch(@Param("list") List<T> list);

    /*
     * insertOrUpdateBatch 批量插入或者更新数据
     */
    Integer insertOrUpdateBatch(@Param("list") List<T> list);

    /*
     * selectList 根据参数查询数据列表
     */
    List<T> selectList(@Param("query") P p);

    /*
     * selectCount 根据参数查询数据条数
     */
    Integer selectCount(@Param("query") P p);
}
