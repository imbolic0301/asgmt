package com.example.persistence.mapper;

import com.example.persistence.entity.BookEntity;
import com.example.persistence.entity.ConsignmentHistoryEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ConsignmentMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert(
        """
            INSERT INTO book_info (
                user_id
                , title
                , ISBN
                , price
                , created_datetime
                , is_rentable
                , is_del
            ) VALUES (
                #{userId}
                , #{title}
                , #{isbn}
                , #{price}
                , now()
                , 1
                , 0
            )
        """
    )
    void createNewBook(BookEntity entity);

    @Options(useGeneratedKeys = true, keyProperty = "seq")
    @Insert(
            """
                INSERT INTO consignment_history (
                    user_id
                    , book_id
                    , title
                    , isbn
                    , price
                    , created_datetime
                ) VALUES (
                    #{userId}
                    , #{bookId}
                    , #{title}
                    , #{isbn}
                    , #{price}
                    , now()
                )
            """
    )
    void createHistory(ConsignmentHistoryEntity entity);
}
