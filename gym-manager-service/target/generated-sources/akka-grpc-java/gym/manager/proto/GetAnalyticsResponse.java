// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ManagerService.proto

package gym.manager.proto;

/**
 * Protobuf type {@code gymmanager.GetAnalyticsResponse}
 */
public final class GetAnalyticsResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:gymmanager.GetAnalyticsResponse)
    GetAnalyticsResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GetAnalyticsResponse.newBuilder() to construct.
  private GetAnalyticsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GetAnalyticsResponse() {
    cartId_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new GetAnalyticsResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return gym.manager.proto.ManagerService.internal_static_gymmanager_GetAnalyticsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return gym.manager.proto.ManagerService.internal_static_gymmanager_GetAnalyticsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            gym.manager.proto.GetAnalyticsResponse.class, gym.manager.proto.GetAnalyticsResponse.Builder.class);
  }

  public static final int CARTID_FIELD_NUMBER = 1;
  private volatile java.lang.Object cartId_;
  /**
   * <code>string cartId = 1;</code>
   * @return The cartId.
   */
  @java.lang.Override
  public java.lang.String getCartId() {
    java.lang.Object ref = cartId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      cartId_ = s;
      return s;
    }
  }
  /**
   * <code>string cartId = 1;</code>
   * @return The bytes for cartId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getCartIdBytes() {
    java.lang.Object ref = cartId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      cartId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int COUNT_FIELD_NUMBER = 2;
  private long count_;
  /**
   * <code>int64 count = 2;</code>
   * @return The count.
   */
  @java.lang.Override
  public long getCount() {
    return count_;
  }

  public static final int IN_FIELD_NUMBER = 3;
  private boolean in_;
  /**
   * <code>bool in = 3;</code>
   * @return The in.
   */
  @java.lang.Override
  public boolean getIn() {
    return in_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(cartId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, cartId_);
    }
    if (count_ != 0L) {
      output.writeInt64(2, count_);
    }
    if (in_ != false) {
      output.writeBool(3, in_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(cartId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, cartId_);
    }
    if (count_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, count_);
    }
    if (in_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(3, in_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof gym.manager.proto.GetAnalyticsResponse)) {
      return super.equals(obj);
    }
    gym.manager.proto.GetAnalyticsResponse other = (gym.manager.proto.GetAnalyticsResponse) obj;

    if (!getCartId()
        .equals(other.getCartId())) return false;
    if (getCount()
        != other.getCount()) return false;
    if (getIn()
        != other.getIn()) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + CARTID_FIELD_NUMBER;
    hash = (53 * hash) + getCartId().hashCode();
    hash = (37 * hash) + COUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getCount());
    hash = (37 * hash) + IN_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getIn());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static gym.manager.proto.GetAnalyticsResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static gym.manager.proto.GetAnalyticsResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(gym.manager.proto.GetAnalyticsResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code gymmanager.GetAnalyticsResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:gymmanager.GetAnalyticsResponse)
      gym.manager.proto.GetAnalyticsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return gym.manager.proto.ManagerService.internal_static_gymmanager_GetAnalyticsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return gym.manager.proto.ManagerService.internal_static_gymmanager_GetAnalyticsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              gym.manager.proto.GetAnalyticsResponse.class, gym.manager.proto.GetAnalyticsResponse.Builder.class);
    }

    // Construct using gym.manager.proto.GetAnalyticsResponse.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      cartId_ = "";

      count_ = 0L;

      in_ = false;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return gym.manager.proto.ManagerService.internal_static_gymmanager_GetAnalyticsResponse_descriptor;
    }

    @java.lang.Override
    public gym.manager.proto.GetAnalyticsResponse getDefaultInstanceForType() {
      return gym.manager.proto.GetAnalyticsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public gym.manager.proto.GetAnalyticsResponse build() {
      gym.manager.proto.GetAnalyticsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public gym.manager.proto.GetAnalyticsResponse buildPartial() {
      gym.manager.proto.GetAnalyticsResponse result = new gym.manager.proto.GetAnalyticsResponse(this);
      result.cartId_ = cartId_;
      result.count_ = count_;
      result.in_ = in_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof gym.manager.proto.GetAnalyticsResponse) {
        return mergeFrom((gym.manager.proto.GetAnalyticsResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(gym.manager.proto.GetAnalyticsResponse other) {
      if (other == gym.manager.proto.GetAnalyticsResponse.getDefaultInstance()) return this;
      if (!other.getCartId().isEmpty()) {
        cartId_ = other.cartId_;
        onChanged();
      }
      if (other.getCount() != 0L) {
        setCount(other.getCount());
      }
      if (other.getIn() != false) {
        setIn(other.getIn());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              cartId_ = input.readStringRequireUtf8();

              break;
            } // case 10
            case 16: {
              count_ = input.readInt64();

              break;
            } // case 16
            case 24: {
              in_ = input.readBool();

              break;
            } // case 24
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }

    private java.lang.Object cartId_ = "";
    /**
     * <code>string cartId = 1;</code>
     * @return The cartId.
     */
    public java.lang.String getCartId() {
      java.lang.Object ref = cartId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cartId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string cartId = 1;</code>
     * @return The bytes for cartId.
     */
    public com.google.protobuf.ByteString
        getCartIdBytes() {
      java.lang.Object ref = cartId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        cartId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string cartId = 1;</code>
     * @param value The cartId to set.
     * @return This builder for chaining.
     */
    public Builder setCartId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      cartId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string cartId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearCartId() {
      
      cartId_ = getDefaultInstance().getCartId();
      onChanged();
      return this;
    }
    /**
     * <code>string cartId = 1;</code>
     * @param value The bytes for cartId to set.
     * @return This builder for chaining.
     */
    public Builder setCartIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      cartId_ = value;
      onChanged();
      return this;
    }

    private long count_ ;
    /**
     * <code>int64 count = 2;</code>
     * @return The count.
     */
    @java.lang.Override
    public long getCount() {
      return count_;
    }
    /**
     * <code>int64 count = 2;</code>
     * @param value The count to set.
     * @return This builder for chaining.
     */
    public Builder setCount(long value) {
      
      count_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 count = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearCount() {
      
      count_ = 0L;
      onChanged();
      return this;
    }

    private boolean in_ ;
    /**
     * <code>bool in = 3;</code>
     * @return The in.
     */
    @java.lang.Override
    public boolean getIn() {
      return in_;
    }
    /**
     * <code>bool in = 3;</code>
     * @param value The in to set.
     * @return This builder for chaining.
     */
    public Builder setIn(boolean value) {
      
      in_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool in = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearIn() {
      
      in_ = false;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:gymmanager.GetAnalyticsResponse)
  }

  // @@protoc_insertion_point(class_scope:gymmanager.GetAnalyticsResponse)
  private static final gym.manager.proto.GetAnalyticsResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new gym.manager.proto.GetAnalyticsResponse();
  }

  public static gym.manager.proto.GetAnalyticsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GetAnalyticsResponse>
      PARSER = new com.google.protobuf.AbstractParser<GetAnalyticsResponse>() {
    @java.lang.Override
    public GetAnalyticsResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<GetAnalyticsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<GetAnalyticsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public gym.manager.proto.GetAnalyticsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
