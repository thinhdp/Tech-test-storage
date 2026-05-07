package game.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import game.TestUtility;
import game.domain.Board;
import game.domain.Cell;
import game.domain.Game;
import game.ports.IGenerator;
import game.ports.IResolver;


public class GameServiceTest {

    @Test
    void shouldGenerateNewGame() {
        IGenerator generator = mock(IGenerator.class);
        IResolver resolver = mock(IResolver.class);

        Board board = TestUtility.emptyBoard();
        
        Game game = new Game(board, board);
        
        when(generator.generate(TestUtility.NUMBER_OF_PRE_FILL_CELLS)).thenReturn(game);

        GameService service = new GameService(generator, resolver);

        Board result = service.generateNewGame(TestUtility.NUMBER_OF_PRE_FILL_CELLS);

        assertEquals(board, result);
    }

    @Test
    void shouldPlayMove() {
        GameService service = new GameService(mock(IGenerator.class), mock(IResolver.class));

        Board board = TestUtility.emptyBoard();

        service.play(board, 0, 0, 7);

        assertEquals(7, board.get(0, 0));
    }

    @Test
    void shouldThrowWhenPlayOnFixedCell() {
        Cell[][] grid = new Cell[9][9];
        grid[0][0] = new Cell(0, 0, true, 5);

        Board board = new Board(grid);

        GameService service = new GameService(mock(IGenerator.class), mock(IResolver.class));

        assertThrows(UnsupportedOperationException.class,
                () -> service.play(board, 0, 0, 9));
    }
    
    @Test
    void shouldDelegateGenerate() {
        IGenerator generator = mock(IGenerator.class);
        IResolver resolver = mock(IResolver.class);

        Board board = TestUtility.emptyBoard();
        Game game = new Game(board, board);
        
        GameService service = new GameService(generator, resolver);
        when(generator.generate(10)).thenReturn(game);

        assertEquals(board, service.generateNewGame(10));
    }

    @Test
    void shouldDelegateSolve() {
        IGenerator generator = mock(IGenerator.class);
        IResolver resolver = mock(IResolver.class);

        Board board = mock(Board.class);

        GameService service = new GameService(generator, resolver);

        service.solve(board);

        verify(resolver).resolve(board);
    }
}