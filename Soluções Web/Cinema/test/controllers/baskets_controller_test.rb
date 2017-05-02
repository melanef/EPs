require 'test_helper'

class BasketsControllerTest < ActionController::TestCase
  test "should get show" do
    get :show
    assert_response :success
  end

end
