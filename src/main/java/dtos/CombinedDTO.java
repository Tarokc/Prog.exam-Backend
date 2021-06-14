package dtos;
import lombok.Data;

@Data

public class CombinedDTO {
    private ChuckNorrisJokeDTO chuck;
    private DadJokeDto dad;
    private AnimeQuoteDTO anime;
    private TronaldDumpDTO tronald;
    private JeopardyDTO jeopardy;

    public CombinedDTO(ChuckNorrisJokeDTO chuck, DadJokeDto dad, AnimeQuoteDTO anime, TronaldDumpDTO tronald, JeopardyDTO jeopardy) {
        this.chuck = chuck;
        this.dad = dad;
        this.anime = anime;
        this.tronald = tronald;
        this.jeopardy = jeopardy;
    }
}