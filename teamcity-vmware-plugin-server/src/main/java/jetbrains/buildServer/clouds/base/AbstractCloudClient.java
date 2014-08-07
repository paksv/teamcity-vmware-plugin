package jetbrains.buildServer.clouds.base;

import java.util.*;
import jetbrains.buildServer.clouds.*;
import jetbrains.buildServer.clouds.base.connector.CloudAsyncTaskExecutor;
import jetbrains.buildServer.clouds.base.errors.CloudErrorMap;
import jetbrains.buildServer.clouds.base.errors.TypedCloudErrorInfo;
import jetbrains.buildServer.clouds.base.errors.UpdatableCloudErrorProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Sergey.Pak
 *         Date: 7/22/2014
 *         Time: 1:49 PM
 */
public abstract class AbstractCloudClient implements CloudClientEx, UpdatableCloudErrorProvider {

  protected final CloudErrorMap myErrorHolder;
  protected Map<String, AbstractCloudImage> myImageMap;
  protected UpdatableCloudErrorProvider myErrorProvider;
  protected final CloudAsyncTaskExecutor myAsyncTaskExecutor;


  public AbstractCloudClient(@NotNull final String description) {
    myErrorHolder = new CloudErrorMap();
    myAsyncTaskExecutor = new CloudAsyncTaskExecutor(description);
  }

  public AbstractCloudClient(@NotNull final String description, @NotNull final Collection<? extends AbstractCloudImage> images) {
    this(description);
    for (AbstractCloudImage image : images) {
      myImageMap.put(image.getName(), image);
    }
  }

  public void dispose() {
    myAsyncTaskExecutor.dispose();
  }

  @NotNull
  public Collection<? extends AbstractCloudImage> getImages() throws CloudException {
    return Collections.unmodifiableCollection(myImageMap.values());
  }

  public void updateErrors(@Nullable final Collection<TypedCloudErrorInfo> errors) {
    myErrorProvider.updateErrors(errors);
  }

  @Nullable
  public CloudErrorInfo getErrorInfo() {
    return myErrorProvider.getErrorInfo();
  }
}