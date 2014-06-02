package ar.com.orkodev.readerswriters.utils
/**
 * Created by orko on 3/29/14.
 */
class StringHelper {

    static final MAX_CHARACTER_TO_CUT = 100

    static def quitarAcentos(String cadena){
        cadena.replaceAll("á", "a").replaceAll("Á", "A").replaceAll("é", "e")
        .replaceAll("É", "E").replaceAll("í","i").replaceAll("Í", "I")
        .replaceAll("ó", "o").replaceAll("Ó", "O").replaceAll("ú", "u")
        .replaceAll("Ú", "U")
    }

    static def cortarStringPorEspacio(String url, int numberOfWord = MAX_CHARACTER_TO_CUT){
        List<String> listWord = url.split(" ")
        def countChar = 0
        def cut = -1
        def index = 0
        for (word in listWord){
            countChar += word.size()
            if (countChar > numberOfWord){
                cut = index
                break
            }else{
                index++
            }
        }
        cut <= 0 ? listWord.join(" ") : listWord.subList(0, cut).join(" ")
    }

    static  def convertStringToFriendlyUrl(String cadena){
        cadena = quitarAcentos(cadena?.toLowerCase())
        cadena = cortarStringPorEspacio(cadena)
        cadena.replaceAll(/\./," ").replaceAll(/\n/,'').replaceAll(/\s{2,}/, " ").replaceAll(/\s/, "-")
    }

}
