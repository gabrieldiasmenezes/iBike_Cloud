package br.com.fiap.ibike;

import br.com.fiap.ibike.model.Moto;
import br.com.fiap.ibike.model.Patio;
import java.time.LocalDate;
import br.com.fiap.ibike.repository.MotoRepository;
import br.com.fiap.ibike.repository.PatioRepository;
import br.com.fiap.ibike.repository.MonitoracaoRepository;
import br.com.fiap.ibike.components.StatusMoto;
import br.com.fiap.ibike.components.StatusPatio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class IbikeApplicationTests {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private MonitoracaoRepository monitoracaoRepository;

    @BeforeEach
    void setUp() {
        // Limpa os dados do banco antes de cada teste
        monitoracaoRepository.deleteAll(); // Primeiro remove os registros de monitoração
        motoRepository.deleteAll(); // Depois remove as motos
        patioRepository.deleteAll(); // Por fim remove os pátios
    }

    @Test
    void contextLoads() {
        // Verifica se o contexto carrega corretamente
        assertNotNull(motoRepository);
        assertNotNull(patioRepository);
    }

    @Test
    void testCriarMoto() {
        // Cria um pátio para associar à moto
        Patio patio = new Patio();
        patio.setId(1L); // Define o ID manualmente
        patio.setNome("Patio Teste");
        patio.setStatus(StatusPatio.DISPONIVEL);
        patio.setCapacidade(10); // Adiciona capacidade obrigatória
        patio = patioRepository.save(patio);

        // Cria uma moto
        Moto moto = new Moto();
        moto.setModelo("Honda CB 500");
        moto.setPlaca("ABC1234");
        moto.setStatus(StatusMoto.NO_PATIO);
        moto.setPatio(patio);
        moto.setKmAtual(0);
        moto.setDataUltimoCheck(LocalDate.now()); // Define a data do último check

        // Salva a moto
        Moto motoSalva = motoRepository.save(moto);

        // Verifica se a moto foi salva corretamente
        assertNotNull(motoSalva.getPlaca());
        assertEquals("Honda CB 500", motoSalva.getModelo());
        assertEquals("ABC1234", motoSalva.getPlaca());
        assertEquals(StatusMoto.NO_PATIO, motoSalva.getStatus());
        assertNotNull(motoSalva.getDataUltimoCheck());
    }

    @Test
    void testCriarPatio() {
        // Cria um pátio
        Patio patio = new Patio();
        patio.setId(2L); // Define o ID manualmente
        patio.setNome("Patio Central");
        patio.setStatus(StatusPatio.DISPONIVEL);
        patio.setCapacidade(10);

        // Salva o pátio
        Patio patioSalvo = patioRepository.save(patio);

        // Verifica se o pátio foi salvo corretamente
        assertNotNull(patioSalvo.getId());
        assertEquals("Patio Central", patioSalvo.getNome());
        assertEquals(StatusPatio.DISPONIVEL, patioSalvo.getStatus());
        assertEquals(10, patioSalvo.getCapacidade());
    }

    @Test
    void testAtualizarStatusMoto() {
        // Cria uma moto
        Moto moto = new Moto();
        moto.setModelo("Honda CB 300");
        moto.setPlaca("XYZ5678");
        moto.setStatus(StatusMoto.NO_PATIO);
        moto.setKmAtual(0);
        moto.setDataUltimoCheck(LocalDate.now()); // Define a data do último check
        
        // Salva a moto
        moto = motoRepository.save(moto);

        // Atualiza o status da moto
        moto.setStatus(StatusMoto.EM_USO);
        Moto motoAtualizada = motoRepository.save(moto);

        // Verifica se o status foi atualizado
        assertEquals(StatusMoto.EM_USO, motoAtualizada.getStatus());
    }
}
