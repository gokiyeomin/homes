package team.gokiyeonmin.imacheater.domain.user.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class RollbackUploadedImageEvent {

    private final String url;
}
