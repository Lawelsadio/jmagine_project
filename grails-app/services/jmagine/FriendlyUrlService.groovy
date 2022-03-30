package jmagine

import grails.gorm.transactions.Transactional

@Transactional
class FriendlyUrlService {

    static transactional = false

    def sanitizeWithDashes(text) {

        if(!text) return ""

        // Preserve escaped octets
        text = text.replaceAll('%([a-fA-F0-9][a-fA-F0-9])','---$1---')
        text = text.replaceAll('%','')
        text = text.replaceAll('---([a-fA-F0-9][a-fA-F0-9])---','%$1')

        // Remove accents
        text = removeAccents(text)

        // To lower case
        text = text.toLowerCase()

        // Kill entities
        text = text.replaceAll('&.+?;','')

        // Dots -> ''
        text = text.replaceAll('\\.','')

        // Remove any character except %a-zA-Z0-9 _-
        text = text.replaceAll('[^%a-zA-Z0-9 _-]', '')

        // Trim
        text = text.trim()

        // Spaces -> dashes
        text = text.replaceAll('\\s+', '-')

        // Dashes -> dash
        text = text.replaceAll('-+', '-')

        // It must end in a letter or digit, otherwise we strip the last char
        if (!text[-1].charAt(0).isLetterOrDigit()) text = text[0..-2]

        return text
    }

    /**
     * Converts all accent characters to ASCII characters.
     *
     * If there are no accent characters, then the string given is just returned.
     *
     */
    private def removeAccents(text) {
        java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
    }
}
