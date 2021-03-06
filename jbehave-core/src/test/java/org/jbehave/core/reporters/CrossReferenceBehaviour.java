package org.jbehave.core.reporters;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jbehave.core.embedder.MatchingStepMonitor.StepMatch;
import org.jbehave.core.embedder.PerformableTree.NormalPerformableScenario;
import org.jbehave.core.embedder.PerformableTree.PerformableRoot;
import org.jbehave.core.embedder.PerformableTree.PerformableScenario;
import org.jbehave.core.embedder.PerformableTree.PerformableSteps;
import org.jbehave.core.embedder.PerformableTree.PerformableStory;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.model.Description;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.StepPattern;
import org.jbehave.core.model.Story;
import org.jbehave.core.steps.StepType;
import org.junit.Test;


public class CrossReferenceBehaviour {

    @Test
    public void shouldProduceXmlAndJsonOutputsOfStoriesAndSteps() throws Exception {

        // Given
        CrossReference crossReference = new CrossReference();

        // When
        PerformableRoot root = performableRoot();
        File outputDirectory = new File("target");
        crossReference.serialise(root, outputDirectory);
        

        // Then
        String expectedXml = resource("xref.xml");
        String actualXml = output(outputDirectory, "xref.xml");

        String expectedJson = resource("xref.json");
        String actualJson = output(outputDirectory, "xref.json");
        
        assertEquals(expectedXml, actualXml);
        assertEquals(expectedJson, actualJson);
    }

    private String resource(String name) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(name)).replaceAll("(?:\\n|\\r)", "");
    }

    private String output(File outputDirectory, String name) throws IOException, FileNotFoundException {
        return IOUtils.toString(new FileReader(new File(outputDirectory, "view/"+name))).replaceAll("(?:\\n|\\r)", "");
    }

    private PerformableRoot performableRoot() {
        PerformableRoot root = new PerformableRoot();
        Story story = new Story("/path/to/story", new Description("An interesting story"), new Meta(Arrays.asList("+theme testing", "+author Mauro")), new Narrative("renovate my house", "customer", "get a loan"), new ArrayList<Scenario>());
        PerformableStory performableStory = new PerformableStory(story, new LocalizedKeywords(), false);
        root.add(performableStory);
        Scenario scenario = new Scenario(Arrays.asList(""));
        PerformableScenario performableScenario = new PerformableScenario(scenario, story.getPath());
        performableStory.add(performableScenario);
        List<StepMatch> stepMatches = new ArrayList<StepMatch>();
        stepMatches.add(new StepMatch(new StepPattern(StepType.GIVEN, "(def)", "[abc]")));
        NormalPerformableScenario normalScenario = new NormalPerformableScenario(scenario);
        normalScenario.addSteps(new PerformableSteps(null, stepMatches));
        performableScenario.useNormalScenario(normalScenario);
        return root;
    }   


}
