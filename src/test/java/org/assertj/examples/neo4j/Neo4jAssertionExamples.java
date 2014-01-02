package org.assertj.examples.neo4j;

import org.assertj.examples.data.neo4j.DragonBallGraph;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.io.IOException;

public class Neo4jAssertionExamples {

    protected static GraphDatabaseService graphDB;
    protected static DragonBallGraph dragonBallGraph;

    @BeforeClass
    public static void prepare_graph() throws IOException {
        graphDB = new TestGraphDatabaseFactory().newImpermanentDatabase();
        dragonBallGraph = new DragonBallGraph(graphDB);
        dragonBallGraph.importGraph(
            Neo4jAssertionExamples.class.getResourceAsStream("/dragonBall.cypher")
        );
    }

    @AfterClass
    public static void cleanUp() {
        graphDB.shutdown();
    }
}
