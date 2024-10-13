package com.isec.jbarros.service;

import com.isec.jbarros.service.dto.NamedEntityDTO;
import com.isec.jbarros.service.dto.TagDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StringDBService {
    private static final String STRING_DB_API_URL = "https://version-11-5.string-db.org/api";
    private static final String OUTPUT_FORMAT = "svg";
    private static final String METHOD = "network";

    private static final String ECHO_QUERY = "echo_query";

    public byte[] generateInteractionGraphSVG(Set<NamedEntityDTO> entities) throws Exception {
        // Extract entity names from NamedEntityDTO
        Set<String> entityNames = entities.stream()
            .map(NamedEntityDTO::getText)
            .collect(Collectors.toSet());

        List<String> namedEntities = extractEntitiesByIOB2Tags(entities);
        entityNames=new HashSet<>(namedEntities);

        // Join entity names for StringDB API request
        String joinedEntities = String.join("%0D", entityNames);
        String encodedEntities = URLEncoder.encode(joinedEntities, "UTF-8");

        // Build the StringDB API URL
        //String apiUrl = STRING_DB_API_URL + "/" + OUTPUT_FORMAT + "/" + METHOD + "?identifiers=" + encodedEntities + "&species=9606";
        //String apiUrl = STRING_DB_API_URL + "/" + OUTPUT_FORMAT + "/" + METHOD + "?identifiers=" + encodedEntities + "&species=9606" + "&" + ECHO_QUERY + "=1";
        String apiUrl = STRING_DB_API_URL + "/" + OUTPUT_FORMAT + "/" + METHOD + "?identifiers=" + encodedEntities;

        // Make the HTTP request to StringDB API
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(apiUrl);
        HttpResponse response = httpClient.execute(request);

        // Retrieve the SVG content as a string
        String svgContent = EntityUtils.toString(response.getEntity());

        // Modify the SVG content to adjust node positions (spacing them out)
        String adjustedSvgContent = adjustSvgNodePositions(svgContent);

        // Save the adjusted SVG content as a local file
        File svgFile = new File("interaction_graph.svg");
        try (FileWriter writer = new FileWriter(svgFile)) {
            writer.write(adjustedSvgContent);
        }

        // Convert the saved file to byte[]
        byte[] fileContent = convertFileToBytes(svgFile);

        // Clean up resources and delete the temporary file
        svgFile.delete();
        httpClient.close();

        return fileContent;  // Return the byte[] of the file content
    }

    /**
     * Convert a file to byte[].
     *
     * @param file the file to be converted
     * @return byte[] of file content
     * @throws IOException
     */
    private byte[] convertFileToBytes(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    /**
     * This method adjusts the positions of the nodes in the SVG string by modifying the 'cx' and 'cy' attributes.
     * @param svgContent The original SVG content from StringDB API
     * @return The adjusted SVG content with nodes spaced out
     */
    private String adjustSvgNodePositions(String svgContent) {
        // Pattern to find the 'cx' and 'cy' attributes in the SVG
        Pattern cxPattern = Pattern.compile("cx=\"(\\d+)\"");
        Pattern cyPattern = Pattern.compile("cy=\"(\\d+)\"");

        // Adjust the cx attributes by adding an offset
        svgContent = adjustSvgAttributes(svgContent, cxPattern, 100);  // Shift each node's x-coordinate by 100px
        svgContent = adjustSvgAttributes(svgContent, cyPattern, 100);  // Shift each node's y-coordinate by 100px

        return svgContent;
    }

    /**
     * Adjusts the value of a given attribute (like cx or cy) by a specified offset.
     * @param svgContent The original SVG content
     * @param pattern The regex pattern for finding the attribute (e.g., cx or cy)
     * @param offset The amount to adjust the attribute by
     * @return The adjusted SVG content
     */
    private String adjustSvgAttributes(String svgContent, Pattern pattern, int offset) {
        Matcher matcher = pattern.matcher(svgContent);
        StringBuffer adjustedContent = new StringBuffer();

        while (matcher.find()) {
            // Get the current value of the attribute (e.g., cx="100")
            int originalValue = Integer.parseInt(matcher.group(1));

            // Adjust the value by adding the offset
            int adjustedValue = originalValue + offset;

            // Replace the value in the SVG content
            matcher.appendReplacement(adjustedContent, "cx=\"" + adjustedValue + "\"");
        }

        matcher.appendTail(adjustedContent);
        return adjustedContent.toString();
    }


    public List<String> extractEntitiesByIOB2Tags(Set<NamedEntityDTO> namedEntities) {
        List<String> result = new ArrayList<>();
        StringBuilder currentEntity = new StringBuilder();
        String previousTag = null;

        for (NamedEntityDTO entity : namedEntities.stream()
            .sorted(Comparator.comparingInt(e -> Integer.parseInt(e.getStartChar())))
            .collect(Collectors.toList())) {
            for (TagDTO tag : entity.getTags()) {
                String label = tag.getLabel();

                if (label.startsWith("B-")) {
                    // If we encounter a new B- tag, save the current entity if it's not empty
                    if (currentEntity.length() > 0) {
                        result.add(currentEntity.toString());  // Add the concatenated entity to the result
                        currentEntity.setLength(0);  // Clear the StringBuilder
                    }
                    // Start a new entity with the current NamedEntity's text
                    currentEntity.append(entity.getText());
                    previousTag = label;
                } else if (label.startsWith("I-") && previousTag != null && previousTag.substring(2).equals(label.substring(2))) {
                    // If the tag is I- and the previous tag is B- of the same type, concatenate the current text
                    currentEntity.append(" ").append(entity.getText());
                }
            }
        }

        // Add the last entity if there's one left in the buffer
        if (currentEntity.length() > 0) {
            result.add(currentEntity.toString());
        }

        System.out.println(result);
        return result;
    }
}
