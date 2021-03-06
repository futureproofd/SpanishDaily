package to.marcus.SpanishDaily;

/**
 * Created by marcus on 11/12/2015
 */
public class Constants {
    public static final String MODE_KEY = "selected_dataset";
    public static final String MAIN_MODE = "unfiltered";
    public static final String FAVORITES_MODE = "favorites";
    public static final String DISMISSED_MODE = "dismissed";
    public static final String SEARCH_MODE = "search";
    //Default DataSet
    public static final String JSON_DEFAULT_DATASET =
            "[{\"exampleEN\":\"After breakfast I’d like to take a walk. A drive in his car is always an adventure.\"," +
                    "\"translation\":\"walk, stroll\"," +
                    "\"visibility\":1," +
                    "\"word\":\"paseo\"," +
                    "\"favorite\":0," +
                    "\"date\":\"April 04, 2016\"," +
                    "\"searched\":0," +
                    "\"exampleESP\":\"Después de desayunar, quisiera dar un paseo. Un paseo en su carro es siempre una aventura.\"," +
                    "\"url\":\"http:\\/\\/assets.merriam-webster.com\\/sc\\/word_of_the_day\\/images\\/1063\\/large.jpg\"," +
                    "\"soundRef\":\"paseo01\"}," +
                    "{\"exampleEN\":\"As soon as he laid his head on the pillow, he fell asleep.\"," +
                    "\"translation\":\"pillow\"," +
                    "\"visibility\":1," +
                    "\"word\":\"almohada\"," +
                    "\"favorite\":0," +
                    "\"date\":\"April 05, 2016\"," +
                    "\"searched\":0," +
                    "\"exampleESP\":\"Apenas apoyó la cabeza en la almohada, se quedó dormido.\"," +
                    "\"url\":\"http:\\/\\/assets.merriam-webster.com\\/sc\\/word_of_the_day\\/images\\/1064\\/large.jpg\"," +
                    "\"soundRef\":\"almoh01\"}," +
                    "{\"exampleEN\":\"So, you can you come then? Fantastic!\"," +
                    "\"translation\":\"barbarous, wild, uncivilized\"," +
                    "\"visibility\":1," +
                    "\"word\":\"bárbaro, bárbara\"," +
                    "\"favorite\":0," +
                    "\"date\":\"April 08, 2016\"," +
                    "\"searched\":0," +
                    "\"exampleESP\":\"¿Puedes venir entonces? ¡Bárbaro!\"," +
                    "\"url\":\"http:\\/\\/assets.merriam-webster.com\\/sc\\/word_of_the_day\\/images\\/1067\\/large.jpg\"," +
                    "\"soundRef\":\"barba06\"}," +
                    "{\"exampleEN\":\"Sancho Panza is an example of a person guided by popular wisdom. The young lawyer was very competent but didn’t have the wisdom that only comes with age.\"," +
                    "\"translation\":\"wisdom\"," +
                    "\"visibility\":1," +
                    "\"word\":\"sabidurí\u00ADa\"," +
                    "\"favorite\":0," +
                    "\"date\":\"April 11, 2016\"," +
                    "\"searched\":0," +
                    "\"exampleESP\":\"Sancho Panza queda como ejemplo de persona guiada por la sabiduría popular. El joven abogado era muy capaz pero no tenía la sabiduría que sólo llega con los años.\"," +
                    "\"url\":\"http:\\/\\/assets.merriam-webster.com\\/sc\\/word_of_the_day\\/images\\/1070\\/large.jpg\"," +
                    "\"soundRef\":\"sabid02\"}," +
                    "{\"exampleEN\":\"She placed her desk next to the window so she could work with natural light. I can’t find Pepe anywhere. The fountain was located in the very center of the square.\"," +
                    "\"translation\":\"SITUAR : to place, to put, to position\"," +
                    "\"visibility\":1," +
                    "\"word\":\"ubicar\"," +
                    "\"favorite\":0," +
                    "\"date\":\"April 12, 2016\"," +
                    "\"searched\":0," +
                    "\"exampleESP\":\"Ubicó su escritorio junto a la ventana para poder trabajar con luz natural. No puedo ubicar a Pepe por ningún lado. La fuente estaba ubicada en el centro mismo de la plaza.\"," +
                    "\"url\":\"http:\\/\\/assets.merriam-webster.com\\/sc\\/word_of_the_day\\/images\\/1071\\/large.jpg\"," +
                    "\"soundRef\":\"ubica02\"}," +
                    "{\"exampleEN\":\"He has no modesty at all when it comes to talking about his achievements.\"," +
                    "\"translation\":\"modesty, reserve\"," +
                    "\"visibility\":1," +
                    "\"word\":\"pudor\"," +
                    "\"favorite\":0," +
                    "\"date\":\"April 18, 2016\"," +
                    "\"searched\":0," +
                    "\"exampleESP\":\"No tiene el más mínimo pudor a la hora de hablar de sus logros.\"," +
                    "\"url\":\"http:\\/\\/assets.merriam-webster.com\\/sc\\/word_of_the_day\\/images\\/1077\\/large.jpg\"," +
                    "\"soundRef\":\"pudor01\"}," +
                    "{\"exampleEN\":\"We'll bring chocolate cookies for the children.\"," +
                    "\"translation\":\"cookie\"," +
                    "\"visibility\":1," +
                    "\"word\":\"galleta\"," +
                    "\"favorite\":0," +
                    "\"date\":\"May 03, 2016\"," +
                    "\"searched\":0," +
                    "\"exampleESP\":\"Nosotros llevaremos galletas de chocolate para los niños.\"," +
                    "\"url\":\"http:\\/\\/assets.merriam-webster.com\\/sc\\/word_of_the_day\\/images\\/1092\\/large.jpg\"," +
                    "\"soundRef\":\"galle02\"}," +
                    "{\"exampleEN\":\"The detective has ascertained that the criminal was lame. The police are investigating whether the suspect has a criminal record.\"," +
                    "\"translation\":\"to find out, to ascertain\"," +
                    "\"visibility\":1," +
                    "\"word\":\"averiguar\"," +
                    "\"favorite\":0," +
                    "\"date\":\"May 04, 2016\"," +
                    "\"searched\":0," +
                    "\"exampleESP\":\"El detective ha averiguado que el delincuente era cojo. La policía está averiguando si el sospechoso tiene antecedentes penales.\"," +
                    "\"url\":\"http:\\/\\/assets.merriam-webster.com\\/sc\\/word_of_the_day\\/images\\/1093\\/large.jpg\"," +
                    "\"soundRef\":\"averi02\"}," +
                    "{\"exampleEN\":\"He was scared of the dog. Juanita is frightened of speaking in public.\"," +
                    "\"translation\":\"TEMOR : fear\"," +
                    "\"visibility\":1," +
                    "\"word\":\"miedo\"," +
                    "\"favorite\":0," +
                    "\"date\":\"May 05, 2016\"," +
                    "\"searched\":0," +
                    "\"exampleESP\":\"Le tenía miedo al perro. A Juanita le da miedo hablar en público.\"," +
                    "\"url\":\"http:\\/\\/assets.merriam-webster.com\\/sc\\/word_of_the_day\\/images\\/1094\\/large.jpg\"," +
                    "\"soundRef\":\"miedo01\"}]"
            ;
}
