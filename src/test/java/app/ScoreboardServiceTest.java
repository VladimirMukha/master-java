package app;

import app.dao.ScoreboardDAOImpl;
import app.service.ScoreboardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ScoreboardServiceTest {
    @InjectMocks
    private ScoreboardServiceImpl scoreboardService;
    @Mock
    private ScoreboardDAOImpl scoreboardDAO;

    @Test
    public void testGetListToScoreboard() {
        List<String> expected = scoreboardService.getListToScoreboard();
        assertNotNull(expected);
    }
}
