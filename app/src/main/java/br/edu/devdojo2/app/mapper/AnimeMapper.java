package br.edu.devdojo2.app.mapper;

import br.edu.devdojo2.app.dto.AnimePostReqDto;
import br.edu.devdojo2.app.dto.AnimeRespDto;
import br.edu.devdojo2.app.model.Anime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//https://medium.com/mobicareofficial/mapstruct-simplificando-mapeamento-de-dtos-em-java-c29135835c68

@Mapper(componentModel = "spring")
public interface AnimeMapper {
    @Mapping(source = "reqDto.id", target = "id")
    @Mapping(source = "reqDto.name", target = "name")
    Anime toModel(AnimePostReqDto reqDto);

    @Mapping(source = "anime.id", target = "id")
    @Mapping(source = "anime.name", target = "name")
    AnimeRespDto toRespDto(Anime anime);
}
