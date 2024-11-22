package com.api.partido.service;

import com.api.partido.domain.Partido;
import com.api.partido.dto.PartidoUpdateRequestDto;
import com.api.partido.repository.PartidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartidoServiceImplTest {

    private PartidoService underTest;
    @Mock
    private PartidoRepository partidoRepositoryMock;


    @BeforeEach
    void setUp() {
        underTest = new PartidoServiceImpl(
                partidoRepositoryMock
        );
    }

    /*
    @Test
    void nombreDelMetodo_contexto_resultadoEsperado
     */

    @Test
    void getAllPartidos_conPartidos_retornaPartidos(){
        //given - dado
        when(partidoRepositoryMock.findAll()).thenReturn(List.of(new Partido()));

        //when - cuando
        List<Partido> result = underTest.getAllPartidos();

        //then - entonces
        Assert.notNull(result,"Result must not be null");
        Assert.notEmpty(result, "Result must not be empty");
    }

    @Test
    void getPartidoById_conPartido_retornaPartidoConId(){
        Long partidoId = 1L;
        Partido fooPartido = new Partido();
        fooPartido.setId(partidoId);

        when(partidoRepositoryMock.findById(partidoId)).thenReturn(Optional.of(fooPartido));

        Optional<Partido> result = underTest.getPartidoById(partidoId);

        Assert.notNull(result, "Partido must not be null");
        Assert.isTrue(partidoId.equals(result.get().getId()), "Partido must not be null");
    }

    @Test
    void savePartido_conPartidoValido_guardaPartidoYLoRetorna(){
        Partido partido = new Partido();

        when(partidoRepositoryMock.save(Mockito.any(Partido.class))).thenReturn(partido);
        Partido result = underTest.savePartido(partido);

        Assert.notNull(result, "Partido must not be null");
    }

    @Test
    public void testUpdatePartido() {
        LocalDateTime fooPartidoDate = LocalDateTime.now();
        LocalDateTime secondFooPartidoDate = LocalDateTime.now();

        // Arrange
        Long partidoId = 1L;
        PartidoUpdateRequestDto updateRequestDto = new PartidoUpdateRequestDto();
        updateRequestDto.setCanchaId(2L);
        updateRequestDto.setDate(fooPartidoDate);
        updateRequestDto.setEstado("FINALIZADO");

        Partido existingPartido = new Partido();
        existingPartido.setId(partidoId);
        existingPartido.setCanchaId(1L);
        existingPartido.setDate(secondFooPartidoDate);
        existingPartido.setEstado("PENDIENTE");

        when(partidoRepositoryMock.findById(partidoId)).thenReturn(Optional.of(existingPartido));
        when(partidoRepositoryMock.save(Mockito.any(Partido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Partido updatedPartido = underTest.updatePartido(partidoId, updateRequestDto);

        // Assert
        assertEquals(2L, updatedPartido.getCanchaId());
        assertEquals(fooPartidoDate, updatedPartido.getDate());
        assertEquals("FINALIZADO", updatedPartido.getEstado());

        // Verify
        verify(partidoRepositoryMock, times(1)).findById(partidoId);
        verify(partidoRepositoryMock, times(1)).save(existingPartido);
    }

    @Test
    public void deletePartido_WithExistentPartido_deletePartido() {
        // Arrange
        Long partidoId = 1L;

        doNothing().when(partidoRepositoryMock).deleteById(partidoId);

        // Act
        underTest.deletePartido(partidoId);

        // Verify
        verify(partidoRepositoryMock, times(1)).deleteById(partidoId);
    }


}
