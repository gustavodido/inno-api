package inno.edu.api.domain.appointment.exceptions;

public class InvalidRatingRangeException extends RuntimeException {
    public InvalidRatingRangeException(int rating) {
        super("Rating value '" + rating + "' must be between 0 and 5.");
    }
}